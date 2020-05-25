package game.base.net0;

import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.concurrent.EventExecutorGroup;
import reactor.core.scheduler.Schedulers;

/**
 * @author yzz
 * 2020/3/20 15:45
 */
public class DefaultClientResources implements ClientResources {
    private EventBus eventBus;

    private EventExecutorGroup eventExecutors;

    public DefaultClientResources() {
        this.eventExecutors = new DefaultEventExecutorGroup(Runtime.getRuntime().availableProcessors(), new DefaultThreadFactory("lettuce-eventExecutorLoop", true));
        this.eventBus = new DefaultEventBus(Schedulers.fromExecutor(this.eventExecutors));
    }

    @Override
    public EventBus eventBus() {
        return eventBus;
    }

    @Override
    public EventExecutorGroup eventExecutorGroup() {
        return eventExecutors;
    }
}
