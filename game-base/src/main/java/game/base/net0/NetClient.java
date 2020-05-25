package game.base.net0;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import reactor.core.publisher.Mono;

import java.io.Closeable;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author yzz
 * 2020/3/20 09:57
 */
public class NetClient {
    private NetConfig netConfig;
    protected static final PooledByteBufAllocator BUF_ALLOCATOR = PooledByteBufAllocator.DEFAULT;
    protected final Set<Closeable> closeableResources = ConcurrentHashMap.newKeySet();
    protected final ConnectionEvents connectionEvents = new ConnectionEvents();
    private final AtomicBoolean shutdown = new AtomicBoolean();


    public NetClient(NetConfig netConfig) {
        this.netConfig = netConfig;
    }

    public void connect() {

        NetWriter writer = new DefaultNetWriter();
        NetConnection connection = new NetConnection(writer, netConfig.getTimeout());
        ConnectionBuilder builder = connectBuilder(connection, writer, netConfig);
        initializeChannel(builder);

    }

    private void initializeChannel(ConnectionBuilder builder) {
        Mono<SocketAddress> socketAddressSupplier = builder.socketAddress();

        CompletableFuture<SocketAddress> socketAddressFuture = new CompletableFuture<>();
        CompletableFuture<Channel> channelReadyFuture = new CompletableFuture<>();

        socketAddressSupplier
                .doOnError(socketAddressFuture::completeExceptionally)
                .doOnNext(socketAddressFuture::complete)
                .subscribe(socketAddress -> {
                    if (channelReadyFuture.isCancelled()) {
                        return;
                    }

                    init0(builder, channelReadyFuture, socketAddress);

                }, channelReadyFuture::completeExceptionally);
    }

    private void init0(ConnectionBuilder builder, CompletableFuture<Channel> channelReadyFuture, SocketAddress socketAddress) {
        Bootstrap bootstrap = builder.getBootstrap();
        DefaultChannelInitializer build = builder.build();
        bootstrap.handler(build);
        CompletableFuture<Boolean> initializedFuture = build.getInitializedFuture();

        ChannelFuture connect = bootstrap.connect(socketAddress);

        channelReadyFuture.whenComplete((channel, throwable) -> {
            if (throwable instanceof CancellationException) {
                connect.cancel(true);
                initializedFuture.cancel(true);
            }
        });

        connect.addListener(future -> {
            if (!future.isSuccess()) {
                channelReadyFuture.completeExceptionally(future.cause());
                return;
            }

            initializedFuture.whenComplete((success, throwable) -> {
                if (throwable == null) {
                    NetConnection connection = builder.getConnection();
                    connection.registerCloseables(closeableResources, connection);
                    channelReadyFuture.complete(connect.channel());
                    return;
                }

                channelReadyFuture.completeExceptionally(throwable);
            });

        });

    }

    private ConnectionBuilder connectBuilder(NetConnection connection, NetWriter writer, NetConfig netConfig) {
        ConnectionBuilder builder = ConnectionBuilder.connectionBuilder();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.option(ChannelOption.WRITE_BUFFER_WATER_MARK, new WriteBufferWaterMark(8 * 1024, 32 * 1024));
        bootstrap.option(ChannelOption.ALLOCATOR, BUF_ALLOCATOR);
        bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS,
                Math.toIntExact(netConfig.getConnectTimeout().toMillis()));
        bootstrap.option(ChannelOption.SO_KEEPALIVE, netConfig.isKeepAlive());
        bootstrap.option(ChannelOption.TCP_NODELAY, netConfig.isTcpNoDelay());
        // todo
        bootstrap.group(new NioEventLoopGroup(1));
        bootstrap.channel(NioSocketChannel.class);

        builder.bootstrap(bootstrap);

        builder.timeout(netConfig.getTimeout());
        builder.setConnection(connection);
        builder.netWriter(writer);
        builder.handler(netConfig.getHandler());
        builder.setSocketAddress(InetSocketAddress.createUnresolved(netConfig.getHost(), netConfig.getPort()));

        builder.setCommandHandlerSupplier(netConfig.getHandler());
        builder.setClientResources(netConfig.getClientResources());
        builder.setConnectionEvents(connectionEvents);

        return builder;
    }

    public void addListener(ConnectionStateListener listener) {
        connectionEvents.addListener(listener);
    }

    public void removeListener(ConnectionStateListener listener) {
        connectionEvents.removeListener(listener);
    }

}
