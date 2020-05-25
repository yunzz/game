package game.gateway;

import game.gateway.server.GatewayServer;

/**
 * @author Yunzhe.Jin
 * 2020/3/28 10:06
 */
public class GatewayApp {
    public static void main(String[] args) {
        new GatewayServer().run();
    }
}
