package game.base.net.client;

import game.base.anno.GuardedBy;
import game.base.core.Game;
import game.base.core.Messages;
import game.base.log.Logs;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import proto.Message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;


/**
 * @author Yunzhe.Jin
 * 2020/4/3 10:04
 */
@GuardedBy("NioEventLoop Executor")
public class TcpClientHandler extends ChannelDuplexHandler {
    private long seq = 1;
    private Map<Long, CompletableFuture<Message>> requestMap = new HashMap<>();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Message message = (Message) msg;
        Logs.common.info(Thread.currentThread().getId() + "-收到消息：" + message);

        if (Messages.isPushMessage(message)) {

            Message.Builder builder = Message.newBuilder(message).clearBroadcastUid();
            List<Long> broadcastUidList = message.getBroadcastUidList();
            for (Long uid : broadcastUidList) {
                if (uid.equals(message.getUid())) {
                    continue;
                }
                builder.setUid(uid);
                Message build = builder.build();
                Logs.common.debug("广播:{}",build);
                Game.pushAndFlushMessage(build);
            }
            return;
        }

        CompletableFuture<Message> future = requestMap.remove(message.getSeq());
        if (future != null) {
            future.complete(message);
        }

    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        WriteMessage message = (WriteMessage) msg;
        Logs.common.info("写消息：" + message);
        final long seq = this.seq;
        Message newMsg = Message.newBuilder(message.getMessage()).setSeq(seq).build();
        // 不需要回复的消息
        if (!Messages.isTellMessage(newMsg)) {
            requestMap.put(seq, message.getFuture());
        }
        this.seq = seq + 1;
        super.write(ctx, newMsg, promise);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Logs.common.error("", cause);
        super.exceptionCaught(ctx, cause);
    }
}
