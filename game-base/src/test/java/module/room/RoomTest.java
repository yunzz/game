package module.room;

import game.base.module.room.RoomManager;
import game.base.module.room.RoomRef;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author Yunzhe.Jin
 * 2020/4/2 13:56
 */
public class RoomTest {

    @Test
    public void test1() throws InterruptedException {
        RoomManager roomManager = new RoomManager(4, () -> new LinkedBlockingQueue<>(128));
        roomManager.start();
        RoomRef roomRef = roomManager.create(new ChatRoom());

        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.allOf(run(1000, roomRef));

        voidCompletableFuture.join();

        TimeUnit.SECONDS.sleep(10);

    }

    private CompletableFuture<Integer>[] run(int i, RoomRef roomRef) throws InterruptedException {
        CompletableFuture[] c = new CompletableFuture[i];
        for (int j = 0; j < i; j++) {
            if (j % 100 == 0)
                TimeUnit.SECONDS.sleep(1);
            c[j] = CompletableFuture.runAsync(() ->
                    {
                        boolean b = roomRef.sendMsg(new CountMsg(1));
                        System.out.println(b);
                    }
            );
        }

        return c;
    }

    @Test
    public void test2() throws InterruptedException {
        RoomManager roomManager = new RoomManager(4, () -> new LinkedBlockingQueue<>(128));
        roomManager.start();
        RoomRef roomRef = roomManager.create(new ChatRoom());
        CompletableFuture.runAsync(() -> {
            while (true) {
                roomRef.sendMsg(new CountMsg(1));
            }

        });

        TimeUnit.SECONDS.sleep(1);

    }

    /**
     * 一个线程
     * 10w 1685ms
     * LinkedBlockingQueue 版本
     *
     * @throws InterruptedException
     */
    @Test
    public void test3() throws InterruptedException {
        RoomManager roomManager = new RoomManager(4, () -> new LinkedBlockingQueue<>(128));
        roomManager.start();
        RoomRef roomRef = roomManager.create(new ChatRoom());
        long s = System.currentTimeMillis();

        // 传递 10万条
        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.allOf(getVoidCompletableFuture(roomRef), getVoidCompletableFuture(roomRef));
        voidCompletableFuture.join();
        System.out.println(System.currentTimeMillis() - s);

    }

    private CompletableFuture<Void> getVoidCompletableFuture(RoomRef roomRef) {
        return CompletableFuture.runAsync(() -> {
            int r = 0;
            for (int i = 0; i < 100000; i++) {
                CompletableFuture<Integer> objectCompletableFuture = roomRef.sendAsyncMsg(new CountMsg(1));
                r = objectCompletableFuture.join();
            }
            System.out.println(roomRef.roomId()+"----" + r);
        });
    }

    /**
     * 一个线程
     * 10w 1685ms
     * ringBuff 版本
     *
     * @throws InterruptedException
     */
    @Test
    public void test4() throws InterruptedException, ExecutionException {
        RoomManager roomManager = new RoomManager(4, () -> new LinkedBlockingQueue<>(128));
        roomManager.start();
        RoomRef roomRef = roomManager.create(new ChatRoom());
        long s = System.currentTimeMillis();

        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.allOf(getVoidCompletableFuture(roomRef), getVoidCompletableFuture(roomRef));
        voidCompletableFuture.get();

        System.out.println(System.currentTimeMillis() - s);

    }

    /**
     *  cpu 8核，16线程
     *  1000W
     *  16线程 20s
     *  32线程 15s
     * @throws InterruptedException
     */
    @Test
    public void test5() throws InterruptedException {
        RoomManager roomManager = new RoomManager(32, () -> new LinkedBlockingQueue<>(128));
        roomManager.start();
        RoomRef roomRef = roomManager.create(new ChatRoom());

        int count = 100;
        RoomRef[] rooms = new RoomRef[count];
        for (int i = 0; i < count; i++) {
            rooms[i] = roomManager.create(new ChatRoom());
        }
        long s = System.currentTimeMillis();

        CompletableFuture[] arr = new CompletableFuture[count];

        for (int i = 0; i < count; i++) {
            arr[i] = getVoidCompletableFuture(rooms[i]);
        }

        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.allOf(arr);
        voidCompletableFuture.join();
        System.out.println(System.currentTimeMillis() - s);

    }
}
