package redis;

import game.base.redis.LettuceClient;
import game.base.redis.RedisManager;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author yzz
 * 2020/3/19 14:10
 */
public class RedisTest {

    @Test
    public void test1() throws InterruptedException {
        RedisManager redisManager = new RedisManager();
        redisManager.start();
        System.out.println("-------------- client 1");
        LettuceClient client = redisManager.getClient();

//        client.set("key", "v");
//        System.out.println("-------------- client 2");
//        LettuceClient k = redisManager.getClient();
        TimeUnit.SECONDS.sleep(111111111);
    }
}
