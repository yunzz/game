package game.world.core;

import game.base.core.Game;
import game.base.log.Logs;
import game.base.module.room.Room;
import game.base.module.room.RoomRef;
import game.base.module.room.UserMessage;
import game.world.core.msg.Tick;
import proto.JoinRoomReq;
import proto.Message;
import proto.MoveMsg;
import proto.MoveTel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Yunzhe.Jin
 * 2020/4/3 17:33
 */
public class FightRoom extends Room {
    private Map<Long, PlayerFight> players = new HashMap<>();
    private List<Long> uids = new ArrayList<>();

    @Override
    public void onStart() {
        RoomRef roomRef = getRoomRef();
        roomRef.newTimeout(timeout -> {
            roomRef.sendMsg(new Tick());
        }, 2, TimeUnit.SECONDS);
    }

    @Override
    public void onMessage(Object msg) {
        if (msg instanceof Tick) {
            log.debug("tick-----------");
            RoomRef roomRef = getRoomRef();
            roomRef.newTimeout(timeout -> {
                roomRef.sendMsg(new Tick());

            }, 2, TimeUnit.SECONDS);
            return;
        }
        UserMessage message = (UserMessage) msg;
        msg = message.msg;
        PlayerFight playerFight = players.computeIfAbsent(message.uid, id -> {
            uids.add(id);
            return new PlayerFight();
        });

        if (msg instanceof MoveTel) {
            Logs.common.debug("Move:{}", msg);
            playerFight.x = ((MoveTel) msg).getX();
            playerFight.y = ((MoveTel) msg).getY();
            broadCast(Message.newBuilder()
                    .setUid(message.uid)
                    .setMoveMsg(MoveMsg.newBuilder().setX(playerFight.x).setY(playerFight.y).build()).build());
        } else if (msg instanceof JoinRoomReq) {

        }
    }

    @Override
    public boolean onError(Object msg, Throwable cause, boolean onEnd) {
        Logs.common.error("", cause);

        return false;
    }

    /**
     * 广播
     */
    private void broadCast(Message msg) {
        Message build = Message.newBuilder(msg).addAllBroadcastUid(uids).build();
        Game.getExecutorManager().getCommonSingleExecutor().submit(() -> {
            Game.pushAndFlushMessage(build);
        });
    }

    private static class PlayerFight {
        public double x;
        public double y;

    }
}
