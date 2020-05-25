package game.base.module.room;

import com.google.common.collect.Sets;
import game.base.log.Logs;
import game.base.utils.Assert;
import game.base.utils.ReflectionUtils0;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Handler;

/**
 * 房间类型的模块，一个房间的所有操作都是单线程执行
 *
 * @author Yunzhe.Jin
 * 2020/4/1 17:13
 */
public abstract class Room {
    protected static final Logger log = LoggerFactory.getLogger(Room.class);
    protected final int roomId;
    RoomRef roomRef;
    protected boolean stopped = false;

    protected Room() {
        this.roomId = RoomManager.roomId.addAndGet(1);
    }

    protected Map<Class<?>, OnMessage<?, ?>> messageHandlerMap = new HashMap<>();

    public int getRoomId() {
        return roomId;
    }

    /**
     * 接收不需要反馈的消息
     *
     * @param msg
     */
    public void onMessage(Object msg) {

    }

    /**
     * 接受异步请求需要反馈的消息
     *
     * @param msg      消息
     * @param complete 反馈future
     */
    public void onMessage(Object msg, CompletableFuture<Object> complete) {

    }

    /**
     * 创建房间后调用
     */
    public void onStart() {

    }

    /**
     * 关闭房间的时候调用
     */
    public void onStop() {

    }

    /**
     * 发生异常调用
     *
     * @param msg
     * @param cause 错误原因
     * @param onEnd 是否在消息 onStop中发生的错误
     * @return {@code true} 如果需要停止房间
     */
    public boolean onError(Object msg, Throwable cause, boolean onEnd) {
        Logs.common.error("", cause);

        return false;
    }

    public RoomRef getRoomRef() {
        return roomRef;
    }

    public void setStopped(boolean stopped) {
        this.stopped = stopped;
    }

    public boolean isStopped() {
        return stopped;
    }


    protected void addMessageHandler(OnMessage<?, ?> messageHandler) {
        Method declaredMethod = ReflectionUtils0.getPublicMethod(messageHandler.getClass(), Sets.newHashSet(Object.class));
        Assert.notNull(declaredMethod, "" + declaredMethod.toString());
        Class<?> messageType = declaredMethod.getParameterTypes()[1];
        messageHandlerMap.put(messageType, messageHandler);
    }
}
