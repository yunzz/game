package netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import org.junit.Test;

import java.sql.Time;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author yzz
 * 2020/3/23 15:20
 */
public class ByteBufTest {


    @Test
    public void test1() {
        ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.directBuffer(8);

        System.out.println(byteBuf);
        byteBuf.writeInt(4);
        dis(byteBuf);

        byteBuf.writeInt(4);
        dis(byteBuf);
        byteBuf.readInt();
        dis(byteBuf);
        byteBuf.discardReadBytes();
        byteBuf.writeBoolean(true);
        dis(byteBuf);
        byteBuf.readInt();

        byteBuf.writeInt(1);
        dis(byteBuf);
    }

    private void dis(ByteBuf byteBuf) {
        System.out.println(byteBuf);
        System.out.println(byteBuf.isReadable());
        System.out.println(byteBuf.isWritable());
        System.out.println(byteBuf.refCnt());
    }

    @Test
    public void test2() {
        // UnpooledByteBufAllocator$InstrumentedUnpooledUnsafeHeapByteBuf(ridx: 0, widx: 0, cap: 10/10)
        ByteBuf byteBuf = Unpooled.buffer(10, 10);

    }

    @Test
    public void test3() throws InterruptedException {
        ConcurrentHashMap<String, String> h = new ConcurrentHashMap<>();
        Long l = 123123123123123L;
        String s1 = "user:123" + l;
        String s2 = "user:123" + l;
        String s3 = "user:123" + l;

        for (int i = 0; i < 2; i++) {
            final int j = i;

            String sss;
            if (j == 0) {
                sss = s2;
            } else {
                sss = s3;
            }
            final String ss = sss;
            new Thread(() -> {
                try {
                    TimeUnit.SECONDS.sleep(1);
                    TimeUnit.SECONDS.sleep(6 * (j ));
                    synchronized (h.computeIfAbsent(ss, k -> k)) {
                        System.out.println("获得锁:" + j);
                        TimeUnit.SECONDS.sleep(8);
                        System.out.println("结束:" + j);
                        h.remove(ss);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }).start();
        }

        synchronized (h.computeIfAbsent(s1, k -> k)) {
            TimeUnit.SECONDS.sleep(5);
            System.out.println("main");
            h.remove(s1);
        }
        TimeUnit.SECONDS.sleep(30);
    }

    @Test
    public void test4() throws InterruptedException {
        Long l = 123123123123123L;
        String s = "user:123" + l;
        String s2 = "user:123" + l;

        new Thread(() -> {
            synchronized (s){
                System.out.println("s");
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();

        new Thread(() -> {
            synchronized (s2){
                System.out.println("s2");
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();

        TimeUnit.SECONDS.sleep(5);
    }

}
