package game.base.core;

import io.netty.channel.ChannelHandlerContext;

/**
 * @author Yunzhe.Jin
 * 2020/4/1 13:47
 */
public interface ConnectionEventHandler {

    void onConnected(ChannelHandlerContext context);

    void onDisconnected(ChannelHandlerContext context);

    void onException(ChannelHandlerContext context, Throwable throwable);
}
