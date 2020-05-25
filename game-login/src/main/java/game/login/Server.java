package game.login;

import game.base.config.AppDataConfig;
import game.base.net.http.HttpServer;
import game.base.net.http.ModuleHandler;
import game.login.base.Global;
import game.login.module.server.ServerHartBeatHandler;
import game.login.module.user.LoginHandler;

/**
 * @author yzz
 * @since 2020/3/16 11:47 下午
 */
public class Server extends HttpServer {

    private static Global global = new Global();
    private AppDataConfig config = new AppDataConfig();

    public Server() {
    }

    @Override
    protected AppDataConfig appDataConfig() {
        return config;
    }

    protected void initHandler() {
        moduleHandlerList.add(new ModuleHandler(new ServerHartBeatHandler()));
        moduleHandlerList.add(new ModuleHandler(new LoginHandler()));
    }

    public void run() throws Exception {
        lifeCycleBox.add(global);
        super.run();
    }

    public static Global getGlobal() {
        return global;
    }
}
