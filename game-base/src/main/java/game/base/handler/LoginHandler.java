package game.base.handler;

import game.base.core.Game;
import game.base.core.MessageHandler;
import game.base.core.Player;
import proto.LoginReq;

import java.util.concurrent.CompletableFuture;

/**
 * @author Yunzhe.Jin
 * 2020/4/1 09:58
 */
public  class LoginHandler implements MessageHandler<LoginReq> {

    @Override
    public CompletableFuture<Player> handle(Player player, LoginReq req) {

        return Game.slowProcess(() -> {
            Player p = new Player();

            return p;
        });
    }
}
