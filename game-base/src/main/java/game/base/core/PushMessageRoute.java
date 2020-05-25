package game.base.core;

import io.netty.channel.ChannelHandlerContext;
import proto.Message;

import java.util.List;

/**
 * 广播消息
 *
 * @author Yunzhe.Jin
 * 2020/4/1 13:38
 */
public interface PushMessageRoute extends ConnectionEventHandler{

    /**
     * 注册连接
     *
     * @param message
     */
    void register(ChannelHandlerContext context, Message message);

    /**
     * 推送消息
     *
     * @param message
     */
    void push(Message message);

    /**
     * 立即推送
     *
     * @param message
     */
    void pushAndFlush(Message message);

}
