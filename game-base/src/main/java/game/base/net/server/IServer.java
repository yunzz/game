package game.base.net.server;

import game.base.LifeCycle;
import game.base.anno.AllLife;
import game.base.core.Handlers;
import game.base.core.IParseMessage;
import game.base.net.client.ClientType;

/**
 * @author Yunzhe.Jin
 * 2020/3/28 10:32
 */
@AllLife
public interface IServer extends LifeCycle {

    /**
     * 注册业务处理程序
     */
    void registerHandler();

    /**
     * 消息处理器
     */
    <T, S> IParseMessage<T, S> getParseMessage();

    /**
     * 返回handler管理
     */
    Handlers getHandler();

    /**
     * 启动服务
     */
    void run();

    /**
     * 服务器为一名
     */
    String serverName();

    /**
     * 服务器类型
     *
     * @return
     */
    ClientType serverType();
}
