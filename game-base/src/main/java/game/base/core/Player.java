package game.base.core;

import game.base.core.work.Work;
import proto.Message;

/**
 * 用户信息
 *
 * @author Yunzhe.Jin
 * 2020/3/28 10:31
 */
public class Player implements IPlayer {
    private Work work;
    private PlayerChannel channel;
    private long id = 0;
    private volatile boolean login = false;
    private String name;

    public void setId(long id) {
        this.id = id;
    }


    @Override
    public long id() {
        return id;
    }

    public PlayerChannel getChannel() {
        return channel;
    }

    public void setChannel(PlayerChannel channel) {
        this.channel = channel;
    }

    /**
     * 向当前玩家推送消息
     */
    public void push(Message message) {
        channel.push(message);
    }

    public Work getWork() {
        return work;
    }

    public void setWork(Work work) {
        this.work = work;
    }

    public boolean isLogin() {
        return login;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Player{" +
                "work=" + work +
                ", channel=" + channel +
                ", id=" + id +
                '}';
    }
}


