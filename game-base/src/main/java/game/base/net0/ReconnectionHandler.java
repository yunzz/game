package game.base.net0;

import io.lettuce.core.internal.LettuceSets;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import io.netty.util.Timeout;
import io.netty.util.Timer;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.net.ConnectException;
import java.net.SocketAddress;
import java.util.Set;
import java.util.concurrent.*;

/**
 * @author yzz
 * 2020/3/20 14:24
 */
public class ReconnectionHandler {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(ReconnectionHandler.class);

    private static final Set<Class<?>> EXECUTION_EXCEPTION_TYPES = LettuceSets.unmodifiableSet(TimeoutException.class,
            CancellationException.class, CmdTimeoutException.class, ConnectException.class);

    private final Bootstrap bootstrap;
    private final Mono<SocketAddress> socketAddressSupplier;
    private final Timer timer;
    private final ExecutorService reconnectWorkers;
    private TimeUnit timeoutUnit = TimeUnit.SECONDS;
    private long timeout = 60;

    private volatile CompletableFuture<Channel> currentFuture;
    private volatile boolean reconnectSuspended;

    public ReconnectionHandler(Bootstrap bootstrap, Mono<SocketAddress> socketAddressSupplier, Timer timer, ExecutorService reconnectWorkers) {
        this.bootstrap = bootstrap;
        this.socketAddressSupplier = socketAddressSupplier;
        this.timer = timer;
        this.reconnectWorkers = reconnectWorkers;
    }

    public boolean isReconnectSuspended() {
        return false;
    }

    public Tuple2<CompletableFuture<Channel>, CompletableFuture<SocketAddress>> reconnect() {
        CompletableFuture<Channel> future = new CompletableFuture<>();
        CompletableFuture<SocketAddress> address = new CompletableFuture<>();

        socketAddressSupplier.subscribe(socketAddress -> {
            address.complete(socketAddress);
            if (future.isCancelled()) {
                return;
            }

            r0(future, socketAddress);
        }, ex -> {
            if (!address.isDone()) {
                address.completeExceptionally(ex);
            }
            future.completeExceptionally(ex);
        });
        this.currentFuture = future;
        return null;
    }

    private void r0(CompletableFuture<Channel> result, SocketAddress remoteAddress) {
        ChannelFuture connectFuture = bootstrap.connect(remoteAddress);
        ChannelPromise initFuture = connectFuture.channel().newPromise();

        logger.info("正在连接.. {}", remoteAddress);
        result.whenComplete((channel, throwable) -> {
            if (throwable instanceof CancellationException) {
                connectFuture.cancel(true);
                initFuture.cancel(true);
            }
        });

        initFuture.addListener((ChannelFuture future) -> {
            if (future.cause() != null) {
                connectFuture.cancel(true);
                close(future.channel());
                result.completeExceptionally(future.cause());
            } else {
                result.complete(connectFuture.channel());
            }
        });

        connectFuture.addListener((ChannelFuture it) -> {

            if (it.cause() != null) {

                initFuture.tryFailure(it.cause());
                return;
            }

            ChannelPipeline pipeline = it.channel().pipeline();
            DefaultChannelInitializer channelInitializer = pipeline.get(DefaultChannelInitializer.class);
            if (channelInitializer == null) {

                initFuture.tryFailure(new IllegalStateException(
                        "没有找到 DefaultChannelInitializer"));
                return;
            }

            channelInitializer.getInitializedFuture().whenComplete((state, throwable) -> {
                if (throwable != null) {
                    logger.error("DefaultChannelInitializer 初始化错误", throwable);
                    if (isExecutionException(throwable)) {
                        initFuture.tryFailure(throwable);
                        return;
                    }

                    initFuture.tryFailure(throwable);

                    return;
                }

                initFuture.trySuccess();

            });
        });

        Runnable timeoutAction = () -> initFuture.tryFailure(new TimeoutException(String.format("Reconnection attempt exceeded timeout of %d %s ",
                timeout, timeoutUnit)));

        Timeout timeoutHandle = timer.newTimeout(it -> {

            if (connectFuture.isDone() && initFuture.isDone()) {
                return;
            }

            if (reconnectWorkers.isShutdown()) {
                timeoutAction.run();
                return;
            }

            reconnectWorkers.submit(timeoutAction);

        }, this.timeout, timeoutUnit);

        initFuture.addListener(it -> timeoutHandle.cancel());
    }

    private void close(Channel channel) {
        if (channel != null) {
            channel.close();
        }
    }

    void setReconnectSuspended(boolean reconnectSuspended) {
        this.reconnectSuspended = reconnectSuspended;
    }

    long getTimeout() {
        return timeout;
    }

    void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public static boolean isExecutionException(Throwable throwable) {

        for (Class<?> type : EXECUTION_EXCEPTION_TYPES) {
            if (type.isAssignableFrom(throwable.getClass())) {
                return true;
            }
        }

        return false;
    }

    void prepareClose() {

        CompletableFuture<?> currentFuture = this.currentFuture;
        if (currentFuture != null && !currentFuture.isDone()) {
            currentFuture.cancel(true);
        }
    }
}
