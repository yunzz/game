package game.base.net0;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.local.LocalAddress;

import java.net.SocketAddress;

/**
 * @author yzz
 * 2020/3/20 11:19
 */
public class ConnectionEventTrigger extends ChannelInboundHandlerAdapter {

    private ConnectionEvents connectionEvents;
    private NetConnection connection;
    private EventBus eventBus;

    public ConnectionEventTrigger(ConnectionEvents connectionEvents, NetConnection netConnection, EventBus eventBus) {
        this.connectionEvents = connectionEvents;
        this.connection = netConnection;
        this.eventBus = eventBus;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        connectionEvents.fireEventConnected(connection, ctx.channel().remoteAddress());
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        connectionEvents.fireEventDisconnected(connection);
        eventBus.publish(new ConnectionInactivatedEvent(local(ctx),remote(ctx)));
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        connectionEvents.fireEventException(connection, cause);
        super.exceptionCaught(ctx, cause);
    }

    static SocketAddress remote(ChannelHandlerContext ctx) {
        if (ctx.channel() != null && ctx.channel().remoteAddress() != null) {
            return ctx.channel().remoteAddress();
        }
        return new LocalAddress("unknown");
    }

    static SocketAddress local(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        if (channel != null && channel.localAddress() != null) {
            return channel.localAddress();
        }
        return LocalAddress.ANY;
    }
}
