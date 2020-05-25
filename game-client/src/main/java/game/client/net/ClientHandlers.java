package game.client.net;

import com.google.common.collect.Sets;
import game.base.utils.Assert;
import game.base.utils.ReflectionUtils0;
import game.client.ClientMessageHandler;
import proto.Message;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Yunzhe.Jin
 * 2020/4/21 11:24
 */
public class ClientHandlers {
    private final Map<String, ClientMessageHandler<?>> handlerMap = new ConcurrentHashMap<>();


    public void registerHandler(ClientMessageHandler<?> handler) {
        Method declaredMethod = ReflectionUtils0.getPublicMethod(handler.getClass(), Sets.newHashSet(Object.class));
        Assert.notNull(declaredMethod, "" + handler.toString());
        Class<?> messageType = declaredMethod.getParameterTypes()[1];
        String name = messageType.getName();
        this.handlerMap.put(name, handler);
    }

    public <T> ClientMessageHandler<T> getHandler(String name) {
        ClientMessageHandler<?> messageHandler = handlerMap.get(name);
        if (messageHandler == null) {
            messageHandler = handlerMap.get(Message.class.getName());
        }
        return (ClientMessageHandler<T>) messageHandler;
    }

    public boolean isUseTopMessageHandler(String name) {
        return handlerMap.get(name) == null;
    }

}
