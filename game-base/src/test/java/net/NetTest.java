package net;

import game.base.net0.*;
import org.junit.Test;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * @author yzz
 * 2020/3/20 16:08
 */
public class NetTest {
    @Test
    public void test1() throws InterruptedException {
        NetConfig config = new NetConfig();
        config.setHost("localhost");
        config.setPort(9002);
        config.setTimeout(Duration.ofSeconds(3));
        config.setHandlerSupply(() -> new NetHandler(new DefaultNetWriter(),config));
        config.setClientResources(new DefaultClientResources());

        NetClient client = new NetClient(config);

        client.connect();


        TimeUnit.HOURS.sleep(1);

    }
}
