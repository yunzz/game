package game.base.core;

import com.google.common.collect.Sets;
import game.base.LifeCycle;
import game.base.utils.Assert;
import game.base.utils.ReflectionUtils0;
import proto.Message;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 业务注册
 *
 * @author Yunzhe.Jin
 * 2020/3/27 17:57
 */
public class Handlers {

    private final Map<String, MessageHandler<?>> handlerMap = new ConcurrentHashMap<>();


    public void registerHandler(MessageHandler<?> handler) {
        Method declaredMethod = ReflectionUtils0.getPublicMethod(handler.getClass(), Sets.newHashSet(Object.class));
        Assert.notNull(declaredMethod, "" + handler.toString());
        Class<?> messageType = declaredMethod.getParameterTypes()[1];
        String name = messageType.getName();
        this.handlerMap.put(name, handler);
    }

    public <T> MessageHandler<T> getHandler(String name) {
        MessageHandler<?> messageHandler = handlerMap.get(name);
        if (messageHandler == null) {
            messageHandler = handlerMap.get(Message.class.getName());
        }
        return (MessageHandler<T>) messageHandler;
    }

    public boolean isUseTopMessageHandler(String name) {
        return handlerMap.get(name) == null;
    }

    public <T> MessageHandler<T> getMessageHandler() {
        return (MessageHandler<T>) handlerMap.get(Message.class.getName());
    }

    public void setHandler(String name, MessageHandler<?> handler) {
        handlerMap.put(name, handler);
    }


}
