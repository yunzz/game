package game.gateway.core;

import game.base.core.Game;
import game.base.core.Player;
import game.base.core.PlayerChannel;
import game.base.core.PushMessageRoute;
import game.base.log.Logs;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.Attribute;
import proto.Message;

/**
 * @author Yunzhe.Jin
 * 2020/4/3 10:39
 */
public class GatewayMessageRoute implements PushMessageRoute {

    @Override
    public void register(ChannelHandlerContext context, Message message) {

    }

    @Override
    public void push(Message message) {
        PlayerChannel player = Game.getPlayer(message.getUid());
        if (player != null) {
            player.getChannel().write(message);
        }

    }

    @Override
    public void pushAndFlush(Message message) {
        PlayerChannel player = Game.getPlayer(message.getUid());
        if (player != null) {
            player.getChannel().writeAndFlush(message);
        }
    }

    @Override
    public void onConnected(ChannelHandlerContext context) {

    }

    @Override
    public void onDisconnected(ChannelHandlerContext context) {
        Attribute<Long> attr = context.channel().attr(PlayerChannel.uidKey);
        if (attr.get() != null) {
            Logs.common.debug("断开连接,移除用户：{}", attr.get());
            Game.removePlayer(attr.get());
        }
    }

    @Override
    public void onException(ChannelHandlerContext context, Throwable throwable) {

    }
}
