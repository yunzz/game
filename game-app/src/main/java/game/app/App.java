package game.app;

import game.app.core.server.AppTcpServer;


/**
 * @author Yunzhe.Jin
 * 2020/3/26 20:12
 */
public class App {
    public static void main(String[] args) {
        new AppTcpServer(9002).run();
    }
}
