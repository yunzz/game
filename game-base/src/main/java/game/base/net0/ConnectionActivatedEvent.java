package game.base.net0;

import java.net.SocketAddress;

/**
 * @author yzz
 * 2020/3/20 13:56
 */
public class ConnectionActivatedEvent extends ConnectionEventSupport{
    public ConnectionActivatedEvent(SocketAddress local, SocketAddress remote) {
        super(local, remote);
    }
}
