package core;

import com.google.protobuf.MessageLiteOrBuilder;
import game.base.core.ParseMessage;
import org.junit.Test;
import proto.Message;

/**
 * @author Yunzhe.Jin
 * 2020/3/31 10:21
 */
public class ParseMessageTest {

    @Test
    public void test1() {

        ParseMessage parseMessage = new ParseMessage();
        parseMessage.parseAllMessage();
        Message message = Message.newBuilder()
                .setSeq(1)
                .setSuccessRes(Message.newBuilder().getSuccessResBuilder().setOk("ooook").build())
                .build();
        MessageLiteOrBuilder payload = parseMessage.payload(message);

        System.out.println(payload);
    }

    @Test
    public void test2() {


        Message message = Message.newBuilder()
                .setSeq(1)
                .setSuccessRes(Message.newBuilder().getSuccessResBuilder().setOk("ooook").build())
                .build();

        System.out.println(message.getContentCase().name());
    }
}
