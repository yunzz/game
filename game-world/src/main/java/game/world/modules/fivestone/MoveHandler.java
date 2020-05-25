package game.world.modules.fivestone;

import game.base.core.MessageHandler;
import game.base.core.Player;
import game.base.core.error.ModuleAssert;
import game.base.module.room.RoomRef;
import game.base.module.room.UserMessage;
import game.world.WorldServer;
import proto.MoveTel;

import static game.base.core.error.ErrorEnum.ROOM_NOT_EXIST;

/**
 * @author Yunzhe.Jin
 * 2020/4/3 17:28
 */
public class MoveHandler implements MessageHandler<MoveTel> {

    @Override
    public Void handle(Player player, MoveTel moveTel) {

        RoomRef playerRoom = WorldServer.getPlayerRoomManager().getPlayerRoom(player.id());

        ModuleAssert.notNull(playerRoom, ROOM_NOT_EXIST);
        playerRoom.sendMsg(new UserMessage(player.id(), moveTel));
        return null;
    }
}
