package game.world.modules.fivestone;

import game.base.core.MessageHandler;
import game.base.core.Player;
import game.base.module.room.RoomRef;
import game.base.module.room.UserMessage;
import game.world.WorldServer;
import game.world.core.PlayerRoomManager;
import game.world.modules.fivestone.room.FiveStoneRoom;
import proto.FindRoomReq;
import proto.FindRoomRes;

import java.util.concurrent.CompletableFuture;

/**
 * @author Yunzhe.Jin
 * 2020/4/22 11:15
 */
public class FindRoomReqHandler implements MessageHandler<FindRoomReq> {

    @Override
    public CompletableFuture<FindRoomRes> handle(Player player, FindRoomReq findRoomReq) {

        PlayerRoomManager playerRoomManager = WorldServer.getPlayerRoomManager();
        RoomRef room = playerRoomManager.findRoom(player.id(), () -> WorldServer.getRoomManager().create(new FiveStoneRoom()));
        //进入游戏
        return room.sendAsyncMsg(new UserMessage(player, findRoomReq));
    }
}
