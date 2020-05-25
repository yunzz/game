package game.base.net0;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.local.LocalAddress;
import io.netty.util.Timeout;
import io.netty.util.Timer;
import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import reactor.util.function.Tuple2;

import java.net.SocketAddress;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 2020/3/20 14:22
 */
@ChannelHandler.Sharable
public class ConnectionWatchdog extends ChannelInboundHandlerAdapter {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(ConnectionWatchdog.class);

    private Channel channel;
    private SocketAddress remoteAddress;

    private final Bootstrap bootstrap;
    private final EventExecutorGroup reconnectWorkers;
    private final ReconnectionHandler reconnectionHandler;
    private final ReconnectionListener reconnectionListener;
    private final Timer timer;
    private final EventBus eventBus;

    private final AtomicBoolean reconnectSchedulerSync;
    private volatile int attempts;
    private volatile boolean armed;
    private volatile boolean listenOnChannelInactive;
    private volatile Timeout reconnectScheduleTimeout;

    public ConnectionWatchdog(Bootstrap bootstrap, EventExecutorGroup reconnectWorkers, ReconnectionHandler reconnectionHandler, ReconnectionListener reconnectionListener, Timer timer, EventBus eventBus) {
        this.bootstrap = bootstrap;
        this.reconnectWorkers = reconnectWorkers;
        this.reconnectionHandler = reconnectionHandler;
        this.reconnectionListener = reconnectionListener;
        this.timer = timer;
        this.eventBus = eventBus;
        this.reconnectSchedulerSync = new AtomicBoolean(false);

    }

    void start() {
        this.armed = true;
        setListenOnChannelInactive(true);
    }

    public void setListenOnChannelInactive(boolean listenOnChannelInactive) {
        this.listenOnChannelInactive = listenOnChannelInactive;
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof ConnectionEvents.Activated) {
            attempts = 0;
        }
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        start();
        reconnectSchedulerSync.set(false);
        channel = ctx.channel();
        reconnectScheduleTimeout = null;
        remoteAddress = channel.remoteAddress();

        logger.debug("channel active");
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.debug("channel inactive");

        if (!armed) {
            logger.debug("没有启动watchdog");
            return;
        }

        channel = null;

        if (listenOnChannelInactive && !reconnectionHandler.isReconnectSuspended()) {
            scheduleReconnect();

        } else {
            logger.debug("没有启动watchdog");
        }

        super.channelInactive(ctx);
    }

    private void scheduleReconnect() {
        if (!isEventLoopGroupActive()) {
            logger.debug("isEventLoopGroupActive() == false");
            return;
        }

        if (!isListenOnChannelInactive()) {
            logger.debug("Skip reconnect scheduling, listener disabled");
            return;
        }

        if (isReconnectSuspended()) {
            logger.debug("Skip reconnect scheduling, reconnect is suspended");
            return;
        }
        if ((channel == null || !channel.isActive()) && reconnectSchedulerSync.compareAndSet(false, true)) {
            attempts++;
            final int attempt = attempts;
            int timeout = 5000;
            this.reconnectScheduleTimeout = timer.newTimeout(it -> {
                reconnectScheduleTimeout = null;
                if (!isEventLoopGroupActive()) {
                    logger.warn("Cannot execute scheduled reconnect timer, reconnect workers are terminated");
                    return;
                }
                reconnectWorkers.submit(() -> {
                    ConnectionWatchdog.this.run(attempt);
                    return null;
                });
            }, timeout, TimeUnit.MILLISECONDS);

            if (!reconnectSchedulerSync.get()) {
                reconnectScheduleTimeout = null;
            }
        }

    }

    private void run(int attempt) {
        reconnectSchedulerSync.set(false);
        reconnectScheduleTimeout = null;

        if (!isEventLoopGroupActive()) {
            logger.debug("isEventLoopGroupActive() == false");
            return;
        }

        if (!isListenOnChannelInactive()) {
            logger.debug("Skip reconnect scheduling, listener disabled");
            return;
        }

        if (isReconnectSuspended()) {
            logger.debug("Skip reconnect scheduling, reconnect is suspended");
            return;
        }

        reconnectionListener.onReconnectAttempt(new ConnectionEvents.Reconnect(attempt));
        Tuple2<CompletableFuture<Channel>, CompletableFuture<SocketAddress>> tuple = reconnectionHandler.reconnect();
        CompletableFuture<Channel> future = tuple.getT1();
        future.whenComplete((c, t) -> {
            if (c != null && t == null) {
                return;
            }

            CompletableFuture<SocketAddress> remoteAddressFuture = tuple.getT2();

            SocketAddress remote = remoteAddress;
            String message = String.format("无法重连 to [%s]: %s", remote,
                    t.getMessage() != null ? t.getMessage() : t.toString());

            logger.info(message);
            eventBus.publish(new ReconnectFailedEvent(LocalAddress.ANY, remote, t, attempt));
            if (!isReconnectSuspended()) {
                scheduleReconnect();
            }
        });
    }

    private boolean isEventLoopGroupActive() {

        return isEventLoopGroupActive(bootstrap.config().group())
                && isEventLoopGroupActive(reconnectWorkers);
    }

    private static boolean isEventLoopGroupActive(EventExecutorGroup executorService) {
        return !(executorService.isShuttingDown());
    }

    public boolean isListenOnChannelInactive() {
        return listenOnChannelInactive;
    }

    public boolean isReconnectSuspended() {
        return reconnectionHandler.isReconnectSuspended();
    }

    public void prepareClose() {

    }
}
