package game.base.net0;


/**
 * @author yzz
 * 2020/3/20 14:35
 */
public interface ReconnectionListener {
    ReconnectionListener NO_OP = new ReconnectionListener() {
        @Override
        public void onReconnectAttempt(ConnectionEvents.Reconnect reconnect) {

        }
    };

    void onReconnectAttempt(ConnectionEvents.Reconnect reconnect);
}
