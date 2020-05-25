package game.base.core.work;

import io.netty.util.concurrent.DefaultThreadFactory;

import java.util.concurrent.Executors;

public class UserWork extends Work {

    public UserWork(int bufferSize) {
        super(bufferSize, new UserWorkHandler(), Executors.newSingleThreadExecutor(new DefaultThreadFactory("用户业务")));
    }
}
