package game.base.net0;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

/**
 * @author yzz
 * 2020/3/20 10:05
 */
public class NetHandler extends ChannelDuplexHandler {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(NetHandler.class);


    private NetWriter netWriter;
    private NetConfig netConfig;

    Channel channel;
    private ByteBuf buffer;
    private LifecycleState lifecycleState = LifecycleState.NOT_CONNECTED;

    public NetHandler(NetWriter netWriter, NetConfig netConfig) {
        this.netWriter = netWriter;
        this.netConfig = netConfig;
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {

        channel = ctx.channel();
        setState(LifecycleState.REGISTERED);
        buffer = ctx.alloc().directBuffer(1024 * 32);

        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        if (channel != null && ctx.channel() != channel) {
            ctx.fireChannelUnregistered();
            return;
        }

        channel = null;
        buffer.release();
        setState(LifecycleState.CLOSED);
        reset();

        super.channelUnregistered(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        if (channel == null || !channel.isActive() || !isConnected()) {
            netWriter.notifyException(cause);

        }

        cause.printStackTrace();
        super.exceptionCaught(ctx, cause);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        setState(LifecycleState.CONNECTED);
        netWriter.notifyChannelActive(ctx.channel());
        if (channel != null) {
            channel.eventLoop().submit((Runnable) () -> channel.pipeline().fireUserEventTriggered(new ConnectionEvents.Activated()));
        }


        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        if (channel != null && ctx.channel() != channel) {
            super.channelInactive(ctx);
            return;
        }
        setState(LifecycleState.DEACTIVATING);
        netWriter.notifyChannelInactive(ctx.channel());
        setState(LifecycleState.DEACTIVATED);
        super.channelInactive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf input = (ByteBuf) msg;
        try {
            buffer.touch("CommandHandler.read(…)");
            if (!buffer.isWritable(input.readableBytes())) {
                buffer.discardReadBytes();
                if (!buffer.isWritable(input.readableBytes())) {
                    logger.warn("不能写入buffer:{},{}",buffer.writableBytes(),input.readableBytes());
                }
            }
            buffer.writeBytes(input);
        } finally {
            input.release();
        }
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        super.write(ctx, msg, promise);
    }

    boolean isConnected() {
        return lifecycleState.ordinal() >= LifecycleState.CONNECTED.ordinal()
                && lifecycleState.ordinal() < LifecycleState.DISCONNECTED.ordinal();
    }

    private void reset() {
        if (buffer.refCnt() > 0) {
            buffer.clear();
        }
    }

    protected void setState(LifecycleState lifecycleState) {

        if (this.lifecycleState != LifecycleState.CLOSED) {
            this.lifecycleState = lifecycleState;
        }
    }

    public boolean isClosed() {
        return lifecycleState == LifecycleState.CLOSED;
    }


    public enum LifecycleState {
        NOT_CONNECTED, REGISTERED, CONNECTED, ACTIVATING, ACTIVE, DISCONNECTED, DEACTIVATING, DEACTIVATED, CLOSED,
    }
}
