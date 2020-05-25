package game.base.module.room;

import io.netty.util.TimerTask;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author Yunzhe.Jin
 * 2020/4/1 17:45
 */
public class RoomRef {

    private RoomAgent roomInner;
    private final int roomId;

    public RoomRef(RoomAgent roomInner, int roomId) {
        this.roomInner = roomInner;
        this.roomId = roomId;
    }

    public boolean sendMsg(Object object) {
        return roomInner.sendMessage(new MsgRoomInner(roomId, object));
    }

    public <T> CompletableFuture<T> sendAsyncMsg(Object msg) {

        MsgAsyncInner<T> inner = new MsgAsyncInner<>(roomId, msg);

        return roomInner.sendAsyncMsg(inner);
    }

    public int roomId() {
        return roomId;
    }

    public void newTimeout(TimerTask task, long delay, TimeUnit unit) {
        roomInner.sendMessage(new MsgTimerTask(roomId, task, delay, unit));
    }

    public void stop(){
        sendMsg(new MsgRoomStopInner(roomId));
    }
}
