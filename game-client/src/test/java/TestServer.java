import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author Yunzhe.Jin
 * 2020/5/15 15:33
 */
public class TestServer {
    public static void main(String[] args) throws InterruptedException {

        EventLoopGroup parentGroup;
        EventLoopGroup childGroup;
        ChannelFuture serverChannelFuture;


        parentGroup = new NioEventLoopGroup(1);
        childGroup = new NioEventLoopGroup(2);

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(parentGroup, childGroup);
        serverBootstrap.channel(NioServerSocketChannel.class);

        serverBootstrap.childHandler(new GameChannelInitializer());

        serverChannelFuture = serverBootstrap.bind(8888).sync();
        serverChannelFuture.channel().closeFuture().addListener(ChannelFutureListener.CLOSE);

    }
}
