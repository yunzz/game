package game.base.net.client;

import game.base.LifeCycle;
import game.base.core.Game;
import io.netty.channel.ChannelFuture;
import proto.Message;
import proto.ServerReq;

import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author Yunzhe.Jin
 * 2020/4/3 10:16
 */
public class TcpClientManager implements IClient, LifeCycle {
    private ConcurrentHashMap<UidType, TcpClient> userClients = new ConcurrentHashMap<>();
    private final Object lock = new Object();
    private Map<Integer, Map<SocketAddress, TcpClient>> clientsTypeMap = new HashMap<>();


    public TcpClient getUserClient(long uid, int type) {
        return userClients.get(new UidType(uid, type));
    }

    public TcpClient registerClient(int type, SocketAddress c) {
        synchronized (lock) {
            Map<SocketAddress, TcpClient> map = clientsTypeMap.computeIfAbsent(type, integer -> new HashMap<>());
            TcpClient client = newClient(c);

            map.put(c, client);
            return client;
        }
    }

    private TcpClient newClient(SocketAddress s) {
        TcpClient tcpClient = new TcpClient(s);
        tcpClient.start();
        ChannelFuture connect = tcpClient.getConnect();
        connect.awaitUninterruptibly(10, TimeUnit.SECONDS);
        if (!connect.isSuccess()) {
            throw new RuntimeException(connect.cause());
        }

        return tcpClient;
    }

    public TcpClient addUserClient(long uid, ClientResolve resolve) {

        TcpClient client;

        int type = resolve.type().type();
        SocketAddress socketAddress = resolve.address(uid);
        synchronized (lock) {
            Map<SocketAddress, TcpClient> socketAddressTcpClientMap = clientsTypeMap.get(type);
            if (socketAddressTcpClientMap == null || socketAddressTcpClientMap.get(socketAddress) == null) {
                TcpClient c = registerClient(type, socketAddress);
                // 发送注册信息
                c.sendFlush(Message.newBuilder().setServerReq(ServerReq.newBuilder()
                        .setType(Game.server.serverType().type())
                        .setServerId(Game.server.serverName()))
                        .build());
            }

            client = clientsTypeMap.get(type).get(socketAddress);

        }
        userClients.put(new UidType(uid, type), client);
        return client;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {
        clientsTypeMap.forEach((integer, socketAddressTcpClientMap) -> {
            socketAddressTcpClientMap.forEach((socketAddress, tcpClient) -> {
                tcpClient.stop();
            });
        });

    }

    private static final class UidType {
        final long uid;
        final int type;

        private UidType(long uid, int type) {
            this.uid = uid;
            this.type = type;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            UidType uidType = (UidType) o;
            return uid == uidType.uid &&
                    type == uidType.type;
        }

        @Override
        public int hashCode() {
            return Objects.hash(uid, type);
        }
    }
}
