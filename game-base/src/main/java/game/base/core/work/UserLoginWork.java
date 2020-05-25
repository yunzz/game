package game.base.core.work;

import io.netty.util.concurrent.DefaultThreadFactory;

import java.util.concurrent.Executors;

public class UserLoginWork extends Work {


    public UserLoginWork(int bufferSize) {
        super(bufferSize, new UserLoginWorkHandler(), Executors.newSingleThreadExecutor(new DefaultThreadFactory("登录处理")));
    }
}
