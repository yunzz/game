package core.queue;

import com.google.thirdparty.publicsuffix.PublicSuffixType;
import com.lmax.disruptor.BlockingWaitStrategy;
import game.base.core.queue.ObjectWrap;
import game.base.core.queue.RingBuffQueue;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author Yunzhe.Jin
 * 2020/4/2 15:59
 */
public class RingBuffQueueTest {
    @Test
    public void test1() {
        RingBuffQueue<Integer> queue = new RingBuffQueue<Integer>(4, ObjectWrap::new, new BlockingWaitStrategy(), (event, sequence, a) -> {
            event.setO(a);
            System.out.println("收到：" + a);
        });

        CompletableFuture.runAsync(() -> {

            while (true){

                queue.take();
            }
        });
        for (int i = 0; i < 50; i++) {
            queue.add(i);
        }
    }
    @Test
    public void test2() {
        RingBuffQueue<Integer> queue = new RingBuffQueue<Integer>(4, ObjectWrap::new, new BlockingWaitStrategy(), (event, sequence, a) -> {
            event.setO(a);
            System.out.println("收到：" + a);
        });

        CompletableFuture.runAsync(() -> {

            while (true){
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                queue.take();
            }
        });
        for (int i = 0; i < 50; i++) {
            boolean offer = queue.offer(i);
            System.out.println("offer:"+offer);
        }
    }
}
