package game.base.core;

import io.netty.channel.ChannelHandlerContext;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Yunzhe.Jin
 * 2020/4/1 13:48
 */
public class ConnectionEvents {
    private final Set<ConnectionEventHandler> listeners = ConcurrentHashMap.newKeySet();

    public void fireEventConnected(ChannelHandlerContext context) {
        for (ConnectionEventHandler listener : listeners) {
            listener.onConnected(context);
        }
    }

    public void fireEventDisconnected(ChannelHandlerContext context) {
        for (ConnectionEventHandler listener : listeners) {
            listener.onDisconnected(context);
        }
    }

    public void fireEventException(ChannelHandlerContext context, Throwable throwable) {
        for (ConnectionEventHandler listener : listeners) {
            listener.onException(context, throwable);
        }
    }

    public void addListener(ConnectionEventHandler listener) {
        listeners.add(listener);
    }

    public void removeListener(ConnectionEventHandler listener) {
        listeners.remove(listener);
    }

}
