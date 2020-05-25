package game.base.net0;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @author yzz
 * 2020/3/20 10:10
 */
public class DefaultNetWriter implements NetWriter {
    protected volatile Channel channel;
    private static final AtomicIntegerFieldUpdater<DefaultNetWriter> STATUS = AtomicIntegerFieldUpdater.newUpdater(
            DefaultNetWriter.class, "status");
    private static final int ST_OPEN = 0;
    private static final int ST_CLOSED = 1;
    private volatile int status = ST_OPEN;
    private final CompletableFuture<Void> closeFuture = new CompletableFuture<>();
    private ConnectionWatchdog connectionWatchdog;

    @Override
    public Cmd write(Cmd cmd) {

        channel.write(cmd);

        return cmd;
    }

    @Override
    public void flush() {
        channel.flush();
    }

    @Override
    public void close() {
        CompletableFuture<Void> voidCompletableFuture = closeAsync();
        voidCompletableFuture.join();

    }

    @Override
    public CompletableFuture<Void> closeAsync() {
        if (isClosed()) {
            return closeFuture;
        }

        if (STATUS.compareAndSet(this, ST_OPEN, ST_CLOSED)) {
            if (connectionWatchdog != null) {
                connectionWatchdog.prepareClose();
            }
            Channel channel = getOpenChannel();
            if (channel != null) {

                ChannelFuture close = channel.close();
                close.addListener(future -> {
                    if (future.isSuccess()) {
                        closeFuture.complete(null);
                    } else {
                        closeFuture.completeExceptionally(future.cause());
                    }
                });

            } else {
                closeFuture.complete(null);
            }

        }
        return closeFuture;
    }

    private Channel getOpenChannel() {

        Channel currentChannel = this.channel;

        return currentChannel;
    }

    @Override
    public void notifyException(Throwable cause) {

    }

    @Override
    public void notifyChannelActive(Channel channel) {
        this.channel = channel;
        if(connectionWatchdog != null){
            connectionWatchdog.start();
        }
    }

    @Override
    public void notifyChannelInactive(Channel channel) {

        if (this.channel == channel) {
            this.channel = null;
        }
    }

    public boolean isClosed() {
        return STATUS.get(this) == ST_CLOSED;
    }
}
