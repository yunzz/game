package game.base.net0;


import java.net.SocketAddress;

/**
 * @author yzz
 * 2020/3/20 13:55
 */
public class DisconnectedEvent extends ConnectionEventSupport {
    public DisconnectedEvent(SocketAddress local, SocketAddress remote) {
        super(local, remote);
    }
}