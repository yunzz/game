package http;

import com.google.common.collect.Maps;
import game.base.http.GameHttpClient;
import game.base.http.msg.ServerHartBeatRequest;
import game.base.http.msg.ServerHartBeatResponse;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yzz
 * 2020/3/23 18:13
 */
public class GameHttpClientTest {
    static String uri = "http://localhost:7000/server/hartBeat";

    @Test
    public void test1() {

        ServerHartBeatRequest request = new ServerHartBeatRequest();
        request.name = "服";
        GameHttpClient.post(request, uri);
        System.out.println("-----------------------------------------");
        GameHttpClient.post(request, uri);
    }

    @Test
    public void test2() {

        ServerHartBeatRequest request = new ServerHartBeatRequest();
        request.name = "服";
        ServerHartBeatResponse post = GameHttpClient.post(request, uri, ServerHartBeatResponse.class);
        System.out.println(post.msg);
        System.out.println("-----------------------------------------");
    }
    @Test
    public void testGet1() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("home","fff");
        String s = GameHttpClient.get(map, "localhost:7000/test1");
        System.out.println("-----"+s);
    }
}
