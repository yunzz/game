package game.base.net0;

import java.net.SocketAddress;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yzz
 * 2020/3/20 11:21
 */
public class ConnectionEvents {
    private final Set<ConnectionStateListener> listeners = ConcurrentHashMap.newKeySet();

    void fireEventConnected(NetConnection connection, SocketAddress socketAddress) {
        for (ConnectionStateListener listener : listeners) {
            listener.onConnected(connection, socketAddress);
        }
    }

    void fireEventDisconnected(NetConnection connection) {
        for (ConnectionStateListener listener : listeners) {
            listener.onDisconnected(connection);
        }
    }

    void fireEventException(NetConnection connection, Throwable throwable) {
        for (ConnectionStateListener listener : listeners) {
            listener.onException(connection, throwable);
        }
    }

    public void addListener(ConnectionStateListener listener) {
        listeners.add(listener);
    }

    public void removeListener(ConnectionStateListener listener) {
        listeners.remove(listener);
    }

    public static class Activated {
    }

    public static class Reset {
    }

    public static class Reconnect {

        private final int attempt;

        public Reconnect(int attempt) {
            this.attempt = attempt;
        }

        public int getAttempt() {
            return attempt;
        }
    }
}
