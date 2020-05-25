package cmf;

import org.junit.Test;

import javax.sound.midi.Soundbank;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author yzz
 * 2020/3/19 00:17
 */
public class CmfTest {

    @Test
    public void test1() throws ExecutionException, InterruptedException {
        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.runAsync(() -> {

            System.out.println("1 " + Thread.currentThread().getId());
        }, Executors.newCachedThreadPool()).whenCompleteAsync((aVoid, throwable) -> {
            System.out.println("2 " + Thread.currentThread().getId());
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).whenComplete((aVoid, throwable) -> {
            System.out.println("4 " + Thread.currentThread().getId());

        });
        voidCompletableFuture.get();
        System.out.println("3 " + Thread.currentThread().getId());
    }

    @Test
    public void test2() throws InterruptedException {

        CompletableFuture<Integer> f = new CompletableFuture<>();
        f.whenComplete((integer, throwable) -> {
            System.out.println("complete");
        });

        TimeUnit.SECONDS.sleep(2);
        f.complete(10);
        TimeUnit.SECONDS.sleep(2);
    }

    @Test
    public void test3() throws InterruptedException, ExecutionException {
        CompletableFuture<Integer> f = new CompletableFuture<>();

        System.out.println(Thread.currentThread().getId());
        f.whenComplete((integer, throwable) -> {
            System.out.println(Thread.currentThread().getId());
            System.out.println("end");
        });
        f.complete(1);

        f.get();
        System.out.println("-------------");
        TimeUnit.SECONDS.sleep(20);
    }


    @Test
    public void test4() throws InterruptedException, ExecutionException {

        CompletableFuture<String> stringCompletableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return ""+Thread.currentThread().getId();
        }).whenComplete((s, throwable) -> {

            System.out.println("完成处理-"+s);
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("完成");
        });

        System.out.println("wwwwait");


        String join = stringCompletableFuture.join();
        System.out.println(join);

    }
}
