package game.base.net0;

import io.netty.channel.*;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import static game.base.net0.ConnectionEventTrigger.local;
import static game.base.net0.ConnectionEventTrigger.remote;

/**
 * @author yzz
 * 2020/3/20 11:45
 */
public class DefaultChannelInitializer extends ChannelInitializer<Channel> {

    private volatile CompletableFuture<Boolean> initializedFuture = new CompletableFuture<>();
    private final ClientResources clientResources;
    private final Duration timeout;
    private final Supplier<List<ChannelHandler>> handlers;


    public DefaultChannelInitializer(ClientResources clientResources, Duration timeout, Supplier<List<ChannelHandler>> buildHandlers) {
        this.clientResources = clientResources;
        this.timeout = timeout;
        this.handlers = buildHandlers;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        if (ch.pipeline().get("DefaultChannelInitializer") == null) {
            ch.pipeline().addLast("DefaultChannelInitializer", new ChannelDuplexHandler() {

                @Override
                public void channelInactive(ChannelHandlerContext ctx) throws Exception {
                    clientResources.eventBus().publish(new DisconnectedEvent(local(ctx), remote(ctx)));

                    if (!initializedFuture.isDone()) {
                        initializedFuture.completeExceptionally(new NetConnectionException("Connection closed prematurely"));
                    }
                    initializedFuture = new CompletableFuture<>();
                    super.channelInactive(ctx);
                }

                @Override
                public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
                    if (evt instanceof ConnectionEvents.Activated) {
                        if (!initializedFuture.isDone()) {
                            initializedFuture.complete(true);
                            clientResources.eventBus().publish(new ConnectionActivatedEvent(local(ctx), remote(ctx)));
                        }
                    }

                    super.userEventTriggered(ctx, evt);
                }

                @Override
                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                    clientResources.eventBus().publish(new ConnectedEvent(local(ctx), remote(ctx)));
                    super.channelActive(ctx);
                }

                @Override
                public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                    if (!initializedFuture.isDone()) {
                        initializedFuture.completeExceptionally(cause);
                    }
                    super.exceptionCaught(ctx, cause);
                }
            });
        }

        for (ChannelHandler handler : handlers.get()) {
            ch.pipeline().addLast(handler);
        }

    }

    public CompletableFuture<Boolean> getInitializedFuture() {
        return initializedFuture;
    }
}
