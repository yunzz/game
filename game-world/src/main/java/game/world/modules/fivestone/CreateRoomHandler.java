package game.world.modules.fivestone;

import game.base.core.MessageHandler;
import game.base.core.Player;
import game.base.core.error.ErrorEnum;
import game.base.core.error.ModuleAssert;
import game.base.module.room.RoomRef;
import game.world.WorldServer;
import game.world.core.PlayerRoomManager;
import game.world.modules.fivestone.room.FiveStoneRoom;
import proto.CreateRoomReq;
import proto.CreateRoomRes;

/**
 * @author Yunzhe.Jin
 * 2020/4/3 17:28
 */
public class CreateRoomHandler implements MessageHandler<CreateRoomReq> {

    @Override
    public CreateRoomRes handle(Player player, CreateRoomReq moveTel) {

        PlayerRoomManager playerRoomManager = WorldServer.getPlayerRoomManager();
        RoomRef playerRoom1 = playerRoomManager.getPlayerRoom(player.id());
        ModuleAssert.isNull(playerRoom1, ErrorEnum.ROOM_ALREADY_IN);
        RoomRef playerRoom = playerRoomManager.createRoom(new FiveStoneRoom());
        return CreateRoomRes.newBuilder().setRoomId(playerRoom.roomId()).build();
    }

}
