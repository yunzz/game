package game.base.core.work;

import game.base.LifeCycle;
import game.base.core.Game;
import game.base.core.Messages;
import game.base.core.PlayerChannel;
import game.base.core.error.ErrorEnum;
import game.base.log.Logs;
import io.netty.channel.Channel;
import io.netty.util.Attribute;
import proto.Message;

/**
 * @author Yunzhe.Jin
 * 2020/3/31 14:27
 */
public class WorkManager implements LifeCycle {

    /**
     * 用户处理work
     */
    private Work[] works;

    /**
     * 非用户请求
     */
    private Work noUserWork;

    /**
     * 登录处理
     */
    private Work userLogin;

    private int size;

    public WorkManager(int size, int bufferSize) {
        this.works = new Work[size];
        this.size = size;
        this.noUserWork = new NoUserWork(bufferSize);
        this.userLogin = new UserLoginWork(bufferSize);

        for (int i = 0; i < size; i++) {
            this.works[i] = new UserWork(bufferSize);
        }
    }

    public void send(Message msg, Channel channel) {
        send(msg, channel, null);
    }

    /**
     * 客户端传过来 msg里面都不带 uid
     *
     * @param msg
     * @param channel
     * @param o
     */
    public void send(Message msg, Channel channel, Object o) {

        long uid = msg.getUid();
        if (uid == 0) {
            Attribute<Long> attr = channel.attr(PlayerChannel.uidKey);
            if (attr.get() != null) {
                uid = attr.get();
                msg = Message.newBuilder(msg).setUid(uid).build();
            }
        }

        Work work;
        if (uid == 0) {
            if (Messages.isLogin(msg)) {
                work = userLogin;
            } else if (Messages.isRequestMessage(msg)) {
                channel.writeAndFlush(Messages.ERROR_MSG.apply(msg.getSeq(), ErrorEnum.ERR_USER_NOT_LOGIN.getId()));
                return;
            } else {
                work = noUserWork;
            }
        } else {
            // 下游service 登录请求也会带uid
            if (Game.getPlayer(uid) == null) {
                if (Messages.isLogin(msg)) {
                    work = userLogin;
                } else {
                    Logs.common.warn("用户未登录:{}", uid);
                    channel.writeAndFlush(Messages.ERROR_MSG.apply(msg.getSeq(), ErrorEnum.ERR_USER_NOT_LOGIN.getId()));
                    return;
                }
            } else if (Messages.isLogin(msg)) {
                Logs.common.warn("用户重复发出登录请求:{}", uid);
                channel.writeAndFlush(Messages.ERROR_MSG.apply(msg.getSeq(), ErrorEnum.ERR_USER_LOGIN.getId()));
                return;
            } else {
                work = next(uid);
            }
        }

        work.send(msg, channel, o);
    }

    public Work next(long id) {
        return works[(int) (id % size)];
    }

    /**
     * 直接发到用户work，必须保证有uid
     *
     * @param msg
     * @param channel
     * @param o
     */
    void innerSendToUser(Message msg, Channel channel, Object o) {
        next(msg.getUid()).send(msg, channel, o);
    }

    @Override
    public void start() {
        for (Work work : this.works) {
            work.start();
        }
        noUserWork.start();
        userLogin.start();

    }

    @Override
    public void stop() {
        for (Work work : this.works) {
            work.stop();
        }
        noUserWork.stop();
        userLogin.stop();

    }

}
