package game.base.net.server;

import game.base.core.Game;
import game.base.core.Messages;
import game.base.log.Logs;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import proto.Message;

/**
 * @author Yunzhe.Jin
 * 2020/3/26 19:33
 */
@ChannelHandler.Sharable
public class TcpServerHandler extends SimpleChannelInboundHandler<Message> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Logs.common.debug("客户端连接成功,{}",ctx.channel());
        Game.getConnectionEvents().fireEventConnected(ctx);
        super.channelActive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
        if (Messages.isServerMessage(msg)) {
            Logs.common.info("注册服务器客户端：{}", msg);
            // 服务器内部消息
            Game.getPushMessageRoute().register(ctx, msg);
        } else {
            Game.send(msg, ctx.channel());
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Logs.common.debug("客户端断开连接:" + ctx.channel().remoteAddress());
        Game.getConnectionEvents().fireEventDisconnected(ctx);
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Logs.common.error("客户端发生异常,{}",ctx.channel(),cause);

        Game.getConnectionEvents().fireEventException(ctx, cause);
        super.exceptionCaught(ctx, cause);
    }
}
