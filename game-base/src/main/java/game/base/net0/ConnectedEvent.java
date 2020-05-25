package game.base.net0;

import java.net.SocketAddress;

/**
 * @author yzz
 * 2020/3/20 14:02
 */
public class ConnectedEvent extends ConnectionEventSupport{
    public ConnectedEvent(SocketAddress local, SocketAddress remote) {
        super(local, remote);
    }
}
