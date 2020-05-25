package game.base.net.client;

import java.net.SocketAddress;

/**
 * @author Yunzhe.Jin
 * 2020/4/3 15:40
 */
public interface ClientResolve {
    ClientType type();

    SocketAddress address(long uid);
}
