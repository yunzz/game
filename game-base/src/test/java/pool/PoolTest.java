package pool;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author yzz
 * 2020/3/18 21:00
 */
public class PoolTest {


    @Test
    public void test1() throws Exception {
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();

        config.setMaxIdle(3);
        config.setMaxTotal(4);
        config.setMaxWaitMillis(-1);
        config.setMinIdle(2);

        ItemPool pool = new ItemPool(config, new ItemFactory());

        System.out.println("6");
        pool.get();
        System.out.println("5");
        pool.get();
        System.out.println("4");
        pool.get();
        System.out.println("3");
        Item item = pool.get();
        System.out.println("2");
        pool.ret(item);
        pool.get();
        System.out.println("1");


        TimeUnit.DAYS.sleep(1);
    }

    @Test
    public void test2() throws Exception {
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();

        config.setMaxIdle(2);
        config.setMaxTotal(4);
        config.setMaxWaitMillis(-1);
        config.setMinIdle(1);
        config.setTestOnBorrow(true);
        config.setTimeBetweenEvictionRunsMillis(6000);

        ItemPool pool = new ItemPool(config, new ItemFactory());

        System.out.println("6");
        Item item1 = pool.get();
        System.out.println("5");
        Item item2 = pool.get();
        System.out.println("4");
        Item item3 = pool.get();
        System.out.println("3");
        Item item4 = pool.get();
        System.out.println("2");
        pool.ret(item1);
        pool.ret(item2);
        pool.ret(item3);
        pool.ret(item4);
        Item item5 = pool.get();
        pool.ret(item5);
        System.out.println("1");


        TimeUnit.DAYS.sleep(1);
    }
}
