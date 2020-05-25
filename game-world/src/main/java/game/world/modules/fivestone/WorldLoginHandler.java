package game.world.modules.fivestone;

import game.base.core.Game;
import game.base.core.Player;
import game.base.handler.LoginHandler;
import game.base.log.Logs;
import proto.LoginReq;

import java.util.concurrent.CompletableFuture;

/**
 * 登录
 *
 * @author Yunzhe.Jin
 * 2020/4/3 14:29
 */
public class WorldLoginHandler extends LoginHandler {

    @Override
    public CompletableFuture<Player> handle(Player player, LoginReq req) {
        return Game.slowProcess(() -> {
            Logs.common.debug("世界服登录：{}", player);
            player.setName(String.valueOf((char) ('a' + player.id() - 1)));
            return player;
        });
    }
}
