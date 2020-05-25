package proto;

import com.google.protobuf.InvalidProtocolBufferException;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author Yunzhe.Jin
 * 2020/4/7 10:10
 */
public class ProtoParseTest {
    private static volatile boolean stop = false;

    @Test
    public void test1() throws InvalidProtocolBufferException {

        Message message2 = Message.newBuilder()
                .setSeq(1)
                .setErrorRes(Message.newBuilder().getErrorResBuilder()
                        .setContent("assssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss")
                        .setIndex(6)
                        .build())
                .build();

        byte[] bytes = message2.toByteArray();

        Message builder = message2.newBuilderForType().mergeFrom(bytes).build();

        int i = 0;

        CompletableFuture.runAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            stop = true;
        });

        while (!stop) {
            i++;
            Message bu = message2.newBuilderForType().mergeFrom(bytes).build();
        }

        System.out.println(i);


    }
}
