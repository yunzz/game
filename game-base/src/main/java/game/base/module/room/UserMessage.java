package game.base.module.room;

import game.base.core.Player;

/**
 * @author Yunzhe.Jin
 * 2020/4/3 17:47
 */
public class UserMessage {

    public final long uid;
    public final Object msg;
    public final String name;

    public UserMessage(long uid, Object msg) {
        this.uid = uid;
        this.msg = msg;
        this.name = "none";
    }

    public UserMessage(Player player, Object msg) {
        this.uid = player.id();
        this.msg = msg;
        this.name = player.getName();
    }
}
