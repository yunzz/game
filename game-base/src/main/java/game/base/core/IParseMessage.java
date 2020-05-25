package game.base.core;

import java.util.function.Supplier;

/**
 * 消息解析
 * @author Yunzhe.Jin
 * 2020/3/31 10:25
 */
public interface IParseMessage<M, BODY> {

    /**
     * 生产body打包消息
     *
     * @param value    业务消息
     * @param supplier 自定义额外操作
     * @param <T>      额外操作传递的类型
     * @return 打包消息
     */
    <T> BODY makeMessage(M value, Supplier<T> supplier);

    /**
     * body中获取 业务消息
     *
     * @param m 收到的body消息
     * @return 返回业务消息
     */
    M payload(BODY m);

    /**
     * 获取业务路径
     *
     * @param message 收到的body消息
     * @return 业务路径
     */
    String path(BODY message);
}
