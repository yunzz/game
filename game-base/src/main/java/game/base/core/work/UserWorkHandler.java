package game.base.core.work;

import game.base.core.Messages;
import game.base.core.Player;
import io.netty.channel.Channel;
import proto.Message;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Yunzhe.Jin
 * 2020/4/4 21:05
 */
public class UserWorkHandler extends WorkHandler {

    // 只在用户work使用
    private Map<Long, Player> playerMap = new HashMap<>();

    @Override
    public void doEvent(WorkEvent event) throws Exception {

        if (event.getObject() instanceof Player) {
            Player player = (Player) event.getObject();
            playerMap.put(player.id(), player);
            return;
        }

        final Channel channel = event.getChannel();
        Message msg = event.getMessage();
        final long uid = msg.getUid();

        Player player = playerMap.get(uid);
        if (Messages.isPushMessage(msg)) {
            player.push(msg);
            return;
        }

        doProcess(msg,channel,player);
    }
}
