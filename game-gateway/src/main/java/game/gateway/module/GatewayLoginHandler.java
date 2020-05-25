package game.gateway.module;

import game.base.core.Game;
import game.base.core.Player;
import game.base.core.error.ErrorEnum;
import game.base.core.error.ModuleAssert;
import game.base.handler.LoginHandler;
import game.base.log.Logs;
import game.base.net.client.TcpClient;
import game.gateway.server.GatewayServer;
import proto.LoginReq;
import proto.Message;

import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 登录
 *
 * @author Yunzhe.Jin
 * 2020/4/3 14:29
 */
public class GatewayLoginHandler extends LoginHandler {
    private static Map<String, Long> ids = new ConcurrentHashMap<>();

    static {

        char v = 'a';
        for (long i = 1; i <= 26; i++) {
            ids.put(String.valueOf(v), i);
            v += 1;
        }

    }

    @Override
    public CompletableFuture<Player> handle(Player player, LoginReq req) {
        CompletableFuture<Player> future1 = Game.slowProcess(() -> {
            Long uid = ids.get(req.getUsername());
            ModuleAssert.notNull(uid, ErrorEnum.ERR_USER_NOT_FOUND);

            player.setId(uid);
            Logs.common.debug("创建用户：" + player.id());

            TcpClient tcpClient = Game.getTcpClientManager().addUserClient(player.id(), GatewayServer.getWorldClientResolve());
            CompletableFuture<Message> future = tcpClient.sendFlush(Message.newBuilder()
                    .setUid(player.id())
                    .setLoginReq(req).build());

            Message ret = null;
            try {
                ret = future.get(5, TimeUnit.SECONDS);
                Logs.common.debug("收到世界服的登录反馈消息:" + ret);
                Logs.common.debug("网关登录：{}", player);
            } catch (Exception e) {
                Logs.common.error("发生错误:", e);
            }
            return player;
        });
        return future1;
    }
}
