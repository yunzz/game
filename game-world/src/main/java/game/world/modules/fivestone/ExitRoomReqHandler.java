package game.world.modules.fivestone;

import game.base.core.MessageHandler;
import game.base.core.Player;
import game.base.core.error.ErrorEnum;
import game.base.core.error.ModuleAssert;
import game.base.module.room.RoomRef;
import game.base.module.room.UserMessage;
import game.world.WorldServer;
import proto.ExitRoomReq;

/**
 * 离开房间
 *
 * @author Yunzhe.Jin
 * 2020/4/24 10:49
 */
public class ExitRoomReqHandler implements MessageHandler<ExitRoomReq> {

    @Override
    public Object handle(Player player, ExitRoomReq req) {
        RoomRef playerRoom = WorldServer.getPlayerRoomManager().getPlayerRoom(player.id());
        ModuleAssert.notNull(playerRoom, ErrorEnum.ROOM_NOT_EXIST);
        playerRoom.sendMsg(new UserMessage(player, req));
        return null;
    }
}
