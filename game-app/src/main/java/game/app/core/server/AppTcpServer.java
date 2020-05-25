package game.app.core.server;

import game.app.module.HartBeatHandler;
import game.app.module.login.LoginHandler;
import game.base.core.AppToGatePushMessageRoute;
import game.base.core.Game;
import game.base.net.client.ClientType;
import game.base.net.server.TcpServer;

/**
 * @author Yunzhe.Jin
 * 2020/3/28 10:21
 */
public class AppTcpServer extends TcpServer {
    public AppTcpServer(int port) {
        super(port);
    }

    @Override
    protected void beforeStart() {
        AppToGatePushMessageRoute pushMessageRoute = new AppToGatePushMessageRoute();
        Game.setPushMessageRoute(pushMessageRoute);
        Game.getConnectionEvents().addListener(pushMessageRoute);
    }

    @Override
    protected void initExcel() {

    }

    @Override
    public void registerHandler() {
        // 心跳
        handler.registerHandler(new HartBeatHandler());
        // 登陆
        handler.registerHandler(new LoginHandler());
    }

    @Override
    public ClientType serverType() {
        return ClientType.APP;
    }

}
