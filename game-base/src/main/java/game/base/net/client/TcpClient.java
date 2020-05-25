package game.base.net.client;

import game.base.LifeCycle;
import game.base.log.Logs;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import proto.Message;

import java.net.SocketAddress;
import java.util.concurrent.CompletableFuture;

/**
 * @author Yunzhe.Jin
 * 2020/4/3 10:04
 */
public class TcpClient implements LifeCycle {
    EventLoopGroup group = new NioEventLoopGroup(1);
    private Bootstrap bootstrap = new Bootstrap();
    private Channel ch;
    private SocketAddress socketAddress;

    public TcpClient(SocketAddress socketAddress) {
        this.socketAddress = socketAddress;
    }

    public Channel getCh() {
        return ch;
    }

    public SocketAddress remoteAddr() {
        return ch.remoteAddress();
    }

    public CompletableFuture<Message> send(Message message) {
        WriteMessage writeMessage = new WriteMessage(message);
        ch.write(writeMessage);
        return writeMessage.getFuture();
    }

    public CompletableFuture<Message> sendFlush(Message message) {
        WriteMessage writeMessage = new WriteMessage(message);
        ch.writeAndFlush(writeMessage);
        return writeMessage.getFuture();

    }

    @Override
    public void start() {
        bootstrap.group(group)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        ChannelPipeline p = ch.pipeline();
                        p.addLast(new TcpClientInitializer());
                    }
                });

        bootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
        bootstrap.option(ChannelOption.WRITE_BUFFER_WATER_MARK, new WriteBufferWaterMark(8 * 1024, 32 * 1024));
        bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000);

        try {
            connect = bootstrap.connect(socketAddress);
            sync = connect.sync();
            ch = sync.channel();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Throwable t) {
            Logs.common.error("", t);
        }
    }
    private   ChannelFuture sync;
    ChannelFuture connect;

    public ChannelFuture getConnect() {
        return connect;
    }

    public ChannelFuture getSync() {
        return sync;
    }

    @Override
    public void stop() {
        group.shutdownGracefully();

    }

}
