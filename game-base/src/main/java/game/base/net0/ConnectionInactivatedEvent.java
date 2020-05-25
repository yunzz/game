package game.base.net0;


import java.net.SocketAddress;

/**
 * @author yzz
 * 2020/3/20 11:34
 */
public class ConnectionInactivatedEvent extends ConnectionEventSupport {
    public ConnectionInactivatedEvent(SocketAddress local, SocketAddress remote) {
        super(local, remote);
    }
}
