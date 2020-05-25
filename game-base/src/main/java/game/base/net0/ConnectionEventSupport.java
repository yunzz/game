package game.base.net0;

import java.net.SocketAddress;

/**
 * @author yzz
 * 2020/3/20 11:34
 */
public class ConnectionEventSupport implements ConnectionEvent {

    private final SocketAddress local;
    private final SocketAddress remote;

    public ConnectionEventSupport(SocketAddress local, SocketAddress remote) {
        this.local = local;
        this.remote = remote;
    }

    public SocketAddress getLocal() {
        return local;
    }

    public SocketAddress getRemote() {
        return remote;
    }

    @Override
    public String toString() {
        return "ConnectionEventSupport{" +
                "local=" + local +
                ", remote=" + remote +
                '}';
    }
}

