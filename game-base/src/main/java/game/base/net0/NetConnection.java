package game.base.net0;

import java.io.Closeable;
import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @author yzz
 * 2020/3/20 09:59
 */
public class NetConnection implements Closeable {
    private static final AtomicIntegerFieldUpdater<NetConnection> CLOSED = AtomicIntegerFieldUpdater.newUpdater(NetConnection.class, "closed");
    private NetWriter netWriter;
    private static final int ST_OPEN = 0;
    private static final int ST_CLOSED = 1;

    private volatile int closed = ST_OPEN;
    private volatile boolean active = true;
    private final CompletableFuture<Void> closeFuture = new CompletableFuture<>();

    private CloseEvents closeEvents = new CloseEvents();
    private Duration timeout;

    public NetConnection(NetWriter netWriter, Duration timeout) {
        this.netWriter = netWriter;
        this.timeout = timeout;
    }

    public void setTimeout(Duration timeout) {
        this.timeout = timeout;
    }

    public Cmd send(Cmd cmd) {
        return netWriter.write(cmd);
    }

    public void close() {
        closeAsync().join();
    }

    public CompletableFuture<Void> closeAsync() {
        if (CLOSED.get(this) == ST_CLOSED) {
            return closeFuture;
        }

        if (CLOSED.compareAndSet(this, ST_OPEN, ST_CLOSED)) {
            active = false;
            CompletableFuture<Void> future = netWriter.closeAsync();
            future.whenComplete((v, t) -> {
                closeEvents.fireEventClosed(this);
                closeEvents = new CloseEvents();
                if (t != null) {
                    closeFuture.completeExceptionally(t);
                } else {
                    closeFuture.complete(v);
                }
            });

        }
        return closeFuture;
    }

    public boolean isClosed() {
        return CLOSED.get(this) == ST_CLOSED;
    }

    public void activated() {
        active = true;
        CLOSED.set(this, ST_OPEN);
    }

    public void deactivated() {
        active = false;
    }

    public NetWriter getNetWriter() {
        return netWriter;
    }

    public boolean isOpen() {
        return active;
    }


    public void flush() {
        getNetWriter().flush();
    }

    public void registerCloseables(Set<Closeable> registry, Closeable... closeables) {
        registry.addAll(Arrays.asList(closeables));

        addListener(resource -> {
            for (Closeable closeable : closeables) {
                if (closeable == NetConnection.this) {
                    continue;
                }

                try {
                    if (closeable instanceof AsyncCloseable) {
                        ((AsyncCloseable) closeable).closeAsync();
                    } else {
                        closeable.close();
                    }
                } catch (IOException e) {

                }
            }

            registry.removeAll(Arrays.asList(closeables));
        });
    }

    protected void addListener(CloseEvents.CloseListener listener) {
        closeEvents.addListener(listener);
    }
}
