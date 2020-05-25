package module.room;

import game.base.module.room.Room;

import java.util.concurrent.CompletableFuture;

/**
 * @author Yunzhe.Jin
 * 2020/4/2 13:56
 */
public class ChatRoom extends Room {
    private int count;

    @Override
    public void onMessage(Object msg) {
        if (msg instanceof CountMsg) {
            count += ((CountMsg) msg).c;
        }

//        System.out.println(String.format("%d,%d", Thread.currentThread().getId(), count));
    }

    @Override
    public void onMessage(Object msg, CompletableFuture<Object> complete) {
        if (msg instanceof CountMsg) {
            count += ((CountMsg) msg).c;
        }

//        System.out.println(String.format("%d,%d", Thread.currentThread().getId(), count));
        complete.complete(count);

    }

}

class CountMsg {
    int c;

    public CountMsg(int c) {
        this.c = c;
    }
}
