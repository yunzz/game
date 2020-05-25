package game.base.net.server;

import game.base.Named;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.DefaultThreadFactory;

/**
 * @author Yunzhe.Jin
 * 2020/3/27 11:20
 */
public abstract class WebsocketServer extends AbstractServer implements Named {
    private Thread thread;

    public WebsocketServer(int port) {
        this.port = port;
        this.thread = new DefaultThreadFactory("websocket-服务").newThread(() -> {

            ServerBootstrap serverBootstrap = new ServerBootstrap();
            NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
            NioEventLoopGroup workerGroup = new NioEventLoopGroup();

            try {
                serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                        .childHandler(new WebsocketServerInitializer())
                        .option(ChannelOption.SO_BACKLOG, 256)
                        .option(ChannelOption.SO_REUSEADDR, true)
                        .bind(port).channel().closeFuture().sync();
            } catch (InterruptedException e) {
                logger.warn("发生中断,退出NettyTcpServer.");
            } catch (Exception e) {
                logger.error("", e);
            } finally {
                bossGroup.shutdownGracefully();
                workerGroup.shutdownGracefully();
            }

            logger.info("退出 WebsocketServer ");
        });
    }

    @Override
    public void start() {

        thread.start();
    }

    @Override
    public void stop() {
        logger.info("关闭 {}", name());
        lifeCycleBox.stop();
    }

    @Override
    public String name() {
        return "WebsocketServer";
    }

    @Override
    public void registerHandler() {

    }
}
