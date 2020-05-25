package game.login.module.server;


import game.base.http.msg.ServerHartBeatRequest;
import game.base.http.msg.ServerHartBeatResponse;
import game.base.net.http.AbstractJsonHandler;
import game.base.net.http.HttpGet;
import game.base.net.http.HttpPost;
import game.base.net.http.JsonHandler;

/**
 * 服务器向登陆服发送心跳， 表示可用
 *
 * @author yzz
 * 2020/3/23 16:52
 */
@HttpPost("/server/hartBeat")
public class ServerHartBeatHandler extends AbstractJsonHandler<ServerHartBeatRequest, ServerHartBeatResponse> {

    @Override
    public ServerHartBeatResponse parse(ServerHartBeatRequest o) {
        System.out.println(o.name);
        ServerHartBeatResponse response = new ServerHartBeatResponse();
        response.msg = "ooo:" + o.name;
        return response;
    }
}
