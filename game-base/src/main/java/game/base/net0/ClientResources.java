package game.base.net0;

import io.netty.util.concurrent.EventExecutorGroup;

/**
 * @author yzz
 * 2020/3/20 11:31
 */
public interface ClientResources {

    EventBus eventBus();

    EventExecutorGroup eventExecutorGroup();
}
