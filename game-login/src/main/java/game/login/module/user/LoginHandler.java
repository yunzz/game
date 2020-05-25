package game.login.module.user;

import game.base.http.msg.LoginRequest;
import game.base.http.msg.LoginResponse;
import game.base.net.http.AbstractJsonHandler;
import game.base.net.http.HttpGet;
import game.base.net.http.JsonHandler;

/**
 * @author yzz
 * 2020/3/23 16:09
 */
@HttpGet("/login2/{uid}")
public class LoginHandler extends AbstractJsonHandler<LoginRequest, LoginResponse> {

    @Override
    public LoginResponse parse(LoginRequest o) {
        LoginResponse response = new LoginResponse();
        System.out.println(o.uid);

        return response;
    }
}
