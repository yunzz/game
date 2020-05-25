package game.base.net.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.DefaultThreadFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Yunzhe.Jin
 * 2020/3/26 19:31
 */
public abstract class TcpServer extends AbstractServer {

    private Channel channel;
    public TcpServer() {
    }

    public TcpServer(int port) {
        this.port = port;
    }

    protected List<ChannelHandler> channelHandlerList() {

        return new ArrayList<>(0);
    }

    @Override
    public void start() {
        // 初始化 tcp
        initTcp();
    }

    private void initTcp() {
        Thread thread = new DefaultThreadFactory("server-服务").newThread(() -> {

            NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
            NioEventLoopGroup workerGroup = new NioEventLoopGroup();
            try {
                ServerBootstrap b = new ServerBootstrap();
                b.group(bossGroup, workerGroup)
                        .option(ChannelOption.SO_REUSEADDR, true)
                        .childOption(ChannelOption.SO_KEEPALIVE, true)
                        .channel(NioServerSocketChannel.class)
                        .handler(new LoggingHandler(LogLevel.INFO))
                        .childHandler(new TcpServerInitializer(channelHandlerList()));
                try {
                    channel = b.bind(port).sync().channel();
                    channel.closeFuture().sync();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    logger.info("Server InterruptedException");
                }
            } finally {
                bossGroup.shutdownGracefully();
                workerGroup.shutdownGracefully();
            }
        });
        thread.start();
    }

    @Override
    public void stop() {
        logger.info("退出服务器");
        ChannelFuture close = channel.close();
        close.awaitUninterruptibly(1, TimeUnit.SECONDS);
        lifeCycleBox.stop();
    }

}
