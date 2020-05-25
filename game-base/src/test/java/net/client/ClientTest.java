package net.client;

import game.base.net.client.TcpClient;
import io.netty.channel.ChannelFuture;
import org.junit.Test;
import proto.HbReq;
import proto.Message;

import java.net.InetSocketAddress;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author Yunzhe.Jin
 * 2020/4/3 10:48
 */
public class ClientTest {
    @Test
    public void Test1() throws InterruptedException {
        TcpClient tcpClient = new TcpClient(InetSocketAddress.createUnresolved("127.0.0.1", 9003));
        tcpClient.start();
        ChannelFuture sync = tcpClient.getConnect();
        ChannelFuture channelFuture = sync.awaitUninterruptibly();
        System.out.println("连接成功");

        Message message = Message.newBuilder().setHbReq(HbReq.newBuilder().build()).build();

        CompletableFuture<Message> messageCompletableFuture = tcpClient.sendFlush(message);
        messageCompletableFuture.whenComplete((message1, throwable) -> {
            System.out.println(Thread.currentThread().getId());
            System.out.println("complete:" + message1);

        });
        System.out.println("end");


        TimeUnit.SECONDS.sleep(100);
    }
}
