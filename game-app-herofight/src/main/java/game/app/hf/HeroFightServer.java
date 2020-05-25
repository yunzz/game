package game.app.hf;

import game.base.net.client.ClientType;
import game.base.net.server.TcpServer;

/**
 * @author Yunzhe.Jin
 * 2020/4/17 11:15
 */
public class HeroFightServer extends TcpServer {

    @Override
    protected void initExcel() {

    }

    @Override
    public void registerHandler() {

    }

    @Override
    public ClientType serverType() {
        return ClientType.APP;
    }
}
