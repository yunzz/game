package game.gateway.server;

import com.google.common.collect.Lists;
import game.base.core.Game;
import game.base.handler.HartBeatHandler;
import game.base.net.client.ClientResolve;
import game.base.net.client.ClientType;
import game.base.net.server.TcpServer;
import game.gateway.core.GatewayMessageRoute;
import game.gateway.core.MessageChannelHandler;
import game.gateway.core.WorldClientResolve;
import game.gateway.excel.PlayerExcelConfig;
import game.gateway.module.GatewayLoginHandler;
import game.gateway.module.MessageProcessHandler;
import io.netty.channel.ChannelHandler;

import java.util.List;

/**
 * @author Yunzhe.Jin
 * 2020/3/28 10:24
 */
public class GatewayServer extends TcpServer {
    private static ClientResolve worldClientResolve = new WorldClientResolve();


    public GatewayServer() {
    }

    @Override
    protected List<ChannelHandler> channelHandlerList() {
        return Lists.newArrayList(new MessageChannelHandler());
    }

    @Override
    protected void beforeStart() {
        GatewayMessageRoute pushMessageRoute = new GatewayMessageRoute();
        Game.setPushMessageRoute(pushMessageRoute);
        Game.getConnectionEvents().addListener(pushMessageRoute);
    }

    @Override
    public void registerHandler() {
        // 心跳
        handler.registerHandler(new HartBeatHandler());
        // 登录
        handler.registerHandler(new GatewayLoginHandler());
        // 其他所有消息处理
        handler.registerHandler(new MessageProcessHandler());
    }

    @Override
    public ClientType serverType() {
        return ClientType.GATEWAY;
    }

    @Override
    protected void initExcel() {
        Game.excelManager.register("player", () -> new PlayerExcelConfig("excel/test.xlsx"));
    }

    public static ClientResolve getWorldClientResolve() {
        return worldClientResolve;
    }
}
