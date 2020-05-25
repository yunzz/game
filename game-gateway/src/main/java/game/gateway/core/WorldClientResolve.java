package game.gateway.core;

import game.base.net.client.ClientResolve;
import game.base.net.client.ClientType;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

/**
 * todo
 * 世界服地址查找
 * @author Yunzhe.Jin
 * 2020/4/6 20:45
 */
public class WorldClientResolve implements ClientResolve {

    @Override
    public ClientType type() {
        return ClientType.WORLD;
    }

    @Override
    public SocketAddress address(long uid) {
        return InetSocketAddress.createUnresolved("127.0.0.1", 9001);
    }
}
