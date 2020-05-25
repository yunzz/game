package game.base.module.room;

import java.util.concurrent.CompletableFuture;

/**
 * @author Yunzhe.Jin
 * 2020/4/2 10:37
 */
public class MsgAsyncInner<T> implements RoomMsg {
    public final int roomId;

    public final Object msg;

    private CompletableFuture<T> complete = new CompletableFuture<>();

    public MsgAsyncInner(int roomId, Object msg) {
        this.roomId = roomId;
        this.msg = msg;
    }

    public CompletableFuture<T> getComplete() {
        return complete;
    }

    @Override
    public int roomId() {
        return roomId;
    }

    @Override
    public Object msg() {
        return msg;
    }
}
