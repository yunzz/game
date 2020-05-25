package game.base.net0;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yzz
 * 2020/3/20 10:19
 */
public class CloseEvents {

    private Set<CloseListener> listeners = ConcurrentHashMap.newKeySet();

    public void fireEventClosed(Object resource) {
        for (CloseListener listener : listeners) {
            listener.resourceClosed(resource);
        }
    }

    public void addListener(CloseListener listener) {
        listeners.add(listener);
    }

    interface CloseListener {
        void resourceClosed(Object resource);
    }
}
