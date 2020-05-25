package game.base.core.queue;

/**
 * todo
 * @author Yunzhe.Jin
 * 2020/4/2 15:46
 */
public interface SimpleQueue<T> extends Iterable<T>{
    boolean offer(T t);

    T take();


    T pollTimeout();

    boolean isEmpty();


}
