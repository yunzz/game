package game.world.modules.fivestone;

import game.base.core.MessageHandler;
import game.base.core.Player;
import game.base.core.error.ErrorEnum;
import game.base.core.error.ModuleAssert;
import game.base.module.room.RoomRef;
import game.base.module.room.UserMessage;
import game.world.WorldServer;
import game.world.core.PlayerRoomManager;
import proto.EnterRoomFvstReq;
import proto.EnterRoomFvstRes;

import java.util.concurrent.CompletableFuture;

/**
 * @author Yunzhe.Jin
 * 2020/4/21 14:50
 */
public class EnterRoomFvstReqHandler implements MessageHandler<EnterRoomFvstReq> {

    @Override
    public CompletableFuture<EnterRoomFvstRes> handle(Player player, EnterRoomFvstReq enterRoomFvstReq) {

        PlayerRoomManager playerRoomManager = WorldServer.getPlayerRoomManager();
        RoomRef playerRoom = playerRoomManager.getRoomById(enterRoomFvstReq.getRoomId());
        ModuleAssert.notNull(playerRoom, ErrorEnum.ROOM_NOT_EXIST);
        RoomRef playerRoom1 = playerRoomManager.getPlayerRoom(player.id());
        // 检查重复进入
        ModuleAssert.isNull(playerRoom1, ErrorEnum.ROOM_RE_ENTER);

        playerRoomManager.joinRoom(player.id(), enterRoomFvstReq.getRoomId());

        return playerRoom.sendAsyncMsg(new UserMessage(player, enterRoomFvstReq));
    }
}
