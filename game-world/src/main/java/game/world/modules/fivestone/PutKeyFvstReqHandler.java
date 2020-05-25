package game.world.modules.fivestone;

import game.base.core.MessageHandler;
import game.base.core.Player;
import game.base.core.error.ErrorEnum;
import game.base.core.error.ModuleAssert;
import game.base.module.room.RoomRef;
import game.base.module.room.UserMessage;
import game.world.WorldServer;
import proto.PutKeyFvstReq;
import proto.PutKeyFvstRes;

import java.util.concurrent.CompletableFuture;

/**
 * @author Yunzhe.Jin
 * 2020/4/21 15:23
 */
public class PutKeyFvstReqHandler implements MessageHandler<PutKeyFvstReq> {
    @Override
    public CompletableFuture<PutKeyFvstRes> handle(Player player, PutKeyFvstReq req) {
        RoomRef playerRoom = WorldServer.getPlayerRoomManager().getPlayerRoom(player.id());
        ModuleAssert.notNull(playerRoom, ErrorEnum.ROOM_NOT_EXIST);
        return playerRoom.sendAsyncMsg(new UserMessage(player, req));
    }
}
