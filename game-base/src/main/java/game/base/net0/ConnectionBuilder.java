package game.base.net0;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelHandler;
import io.netty.util.HashedWheelTimer;
import io.netty.util.Timer;
import reactor.core.publisher.Mono;

import java.net.SocketAddress;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * @author yzz
 * 2020/3/20 10:02
 */
public class ConnectionBuilder {

    private Bootstrap bootstrap;
    private NetConnection connection;
    private Supplier<NetHandler> commandHandlerSupplier;
    private ConnectionEvents connectionEvents;
    private ClientResources clientResources;
    private Duration timeout;
    private SocketAddress socketAddress;
    private ReconnectionListener reconnectionListener = ReconnectionListener.NO_OP;
    private final Timer timer = new HashedWheelTimer();

    public static ConnectionBuilder connectionBuilder() {
        return new ConnectionBuilder();
    }

    public void setConnection(NetConnection connection) {
        this.connection = connection;
    }

    protected List<ChannelHandler> buildHandlers() {
        List<ChannelHandler> handlers = new ArrayList<>();
        handlers.add(new CommandEncoder());
        handlers.add(commandHandlerSupplier.get());
        handlers.add(new ConnectionEventTrigger(connectionEvents, connection, clientResources.eventBus()));
        ConnectionWatchdog watchdog = new ConnectionWatchdog(
                bootstrap,
                clientResources.eventExecutorGroup(),
                new ReconnectionHandler(bootstrap, Mono.just(socketAddress), timer, clientResources.eventExecutorGroup()),
                reconnectionListener,
                timer,
                clientResources.eventBus());
        handlers.add(watchdog);

        return handlers;
    }

    public DefaultChannelInitializer build() {
        return new DefaultChannelInitializer(clientResources, timeout, this::buildHandlers);
    }


    public void netWriter(NetWriter writer) {

    }

    public void handler(Supplier<NetHandler> handlerSupplier) {


    }

    public Mono<SocketAddress> socketAddress() {
        return Mono.just(socketAddress);
    }

    public void setSocketAddress(SocketAddress socketAddress) {
        this.socketAddress = socketAddress;
    }

    public void bootstrap(Bootstrap bootstrap) {

        this.bootstrap = bootstrap;
    }

    public Bootstrap getBootstrap() {
        return bootstrap;
    }

    public void timeout(Duration timeout) {
        this.timeout = timeout;

    }

    public Duration getTimeout() {
        return timeout;
    }

    public Supplier<NetHandler> getCommandHandlerSupplier() {
        return commandHandlerSupplier;
    }

    public void setCommandHandlerSupplier(Supplier<NetHandler> commandHandlerSupplier) {
        this.commandHandlerSupplier = commandHandlerSupplier;
    }

    public ConnectionEvents getConnectionEvents() {
        return connectionEvents;
    }

    public void setConnectionEvents(ConnectionEvents connectionEvents) {
        this.connectionEvents = connectionEvents;
    }

    public ClientResources getClientResources() {
        return clientResources;
    }

    public void setClientResources(ClientResources clientResources) {
        this.clientResources = clientResources;
    }

    public NetConnection getConnection() {
        return connection;
    }
}
