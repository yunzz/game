package game.base.net0;

import reactor.core.publisher.Flux;

/**
 * @author yzz
 * 2020/3/20 11:19
 */
public interface EventBus {
    Flux<Event> get();

    void publish(Event event);
}
