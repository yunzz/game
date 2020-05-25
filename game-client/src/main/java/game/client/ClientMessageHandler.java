package game.client;

import com.google.protobuf.MessageLite;
import game.base.core.Player;

/**
 * @author Yunzhe.Jin
 * 2020/4/21 11:15
 */

public interface ClientMessageHandler<T> {

    /**
     * 处理方法
     *
     * @param player
     * @param t
     * @return 子类必须重写具体返回类型，无返回设置为Void
     */
    void handle(Player player, MessageLite t);

}
