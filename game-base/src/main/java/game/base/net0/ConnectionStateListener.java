package game.base.net0;

import java.net.SocketAddress;

/**
 * @author yzz
 * 2020/3/20 11:21
 */
public interface ConnectionStateListener {

    void onConnected(NetConnection connection, SocketAddress socketAddress);

    void onDisconnected(NetConnection connection);

    void onException(NetConnection connection, Throwable throwable);
}
