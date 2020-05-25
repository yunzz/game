package game.base.module.room;

import game.base.LifeCycle;
import io.netty.util.concurrent.Promise;

import java.util.concurrent.CompletableFuture;

/**
 * @author Yunzhe.Jin
 * 2020/4/2 16:54
 */
public interface RoomAgent extends LifeCycle {

    /**
     * 只发送，不需要结果，
     *
     * @param msg 消息
     * @return 发送是否成功
     */
    boolean sendMessage(RoomMsg msg);


    /**
     * 发送异步消息
     *
     * @param inner
     * @param <T>
     * @return
     */
    <T> CompletableFuture<T> sendAsyncMsg(MsgAsyncInner<T> msg);


    /**
     * 关闭agent
     *
     * @return
     */
    Promise<Object> getClosePromise();
}
