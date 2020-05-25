package game.base.net.server;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * @author Yunzhe.Jin
 * 2020/3/27 11:18
 */
public class WebsocketServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        for (ChannelHandler handler : WebsocketServerHandler.handlers()) {
            ch.pipeline().addLast(handler);
        }
    }
}
