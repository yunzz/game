package game.base.core;

/**
 * @author Yunzhe.Jin
 * 2020/3/27 17:52
 */
public interface MessageHandler<T > {

    /**
     * 处理方法
     * @param player
     * @param t
     * @return 子类必须重写具体返回类型，无返回设置为Void
     */
    Object handle(Player player, T t);

}
