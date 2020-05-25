import org.junit.Test;
import proto.Message;
import proto.Success;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Yunzhe.Jin
 * 2020/4/7 14:33
 */
public class TestUrl {

    @Test
    public void test1() throws MalformedURLException {
        Message m = Message.newBuilder().setSuccessRes(Success.newBuilder().build()).build();
        System.out.println(m.getContentCase().name());
    }
}
