package game.world;

import game.base.core.AppToGatePushMessageRoute;
import game.base.core.Game;
import game.base.module.room.RoomManager;
import game.base.net.client.ClientType;
import game.base.net.server.TcpServer;
import game.world.core.PlayerRoomManager;
import game.world.modules.fivestone.FindRoomReqHandler;
import game.world.modules.fivestone.PutKeyFvstReqHandler;
import game.world.modules.fivestone.CreateRoomHandler;
import game.world.modules.fivestone.EnterRoomFvstReqHandler;
import game.world.modules.fivestone.MoveHandler;
import game.world.modules.fivestone.WorldLoginHandler;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Yunzhe.Jin
 * 2020/4/3 14:16
 */
public class WorldServer extends TcpServer {

    private static RoomManager roomManager = new RoomManager(Runtime.getRuntime().availableProcessors(), LinkedBlockingQueue::new);
    private static PlayerRoomManager playerRoomManager = new PlayerRoomManager();

    // todo 放到上层
    @Override
    protected void beforeStart() {
        AppToGatePushMessageRoute pushMessageRoute = new AppToGatePushMessageRoute();
        Game.setPushMessageRoute(pushMessageRoute);
        Game.getConnectionEvents().addListener(pushMessageRoute);
        lifeCycleBox.add(roomManager);
    }

    public static RoomManager getRoomManager() {
        return roomManager;
    }

    @Override
    protected void initExcel() {

    }

    @Override
    public void registerHandler() {

        handler.registerHandler(new WorldLoginHandler());
        handler.registerHandler(new MoveHandler());
        handler.registerHandler(new CreateRoomHandler());
        handler.registerHandler(new EnterRoomFvstReqHandler());
        handler.registerHandler(new PutKeyFvstReqHandler());
        handler.registerHandler(new FindRoomReqHandler());
    }

    @Override
    public ClientType serverType() {
        return ClientType.WORLD;
    }

    public static PlayerRoomManager getPlayerRoomManager() {
        return playerRoomManager;
    }
}
