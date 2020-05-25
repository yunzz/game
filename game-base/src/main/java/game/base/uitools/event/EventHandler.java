package game.base.uitools.event;

/**
 * @author Yunzhe.Jin
 * 2020/4/7 15:39
 */
public interface EventHandler<T extends Event> {

    void onEvent(T event);
}
