package game.base.module.room;

import io.netty.util.TimerTask;

import java.util.concurrent.TimeUnit;

/**
 * @author Yunzhe.Jin
 * 2020/4/13 15:40
 */
public class MsgTimerTask implements RoomMsg {


    private int roomId;
    private TimerTask task;
    private long delay;
    private TimeUnit unit;

    public MsgTimerTask(int roomId, TimerTask task, long delay, TimeUnit unit) {
        this.roomId = roomId;

        this.task = task;
        this.delay = delay;
        this.unit = unit;
    }


    public TimerTask getTask() {
        return task;
    }

    public long getDelay() {
        return delay;
    }

    public TimeUnit getUnit() {
        return unit;
    }

    @Override
    public int roomId() {
        return this.roomId;
    }

    @Override
    public Object msg() {
        return this;
    }
}
