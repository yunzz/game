package game.base.core;

import com.google.protobuf.Descriptors;
import com.google.protobuf.MessageLite;
import game.base.LifeCycle;
import game.base.utils.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import proto.Message;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author Yunzhe.Jin
 * 2020/3/27 17:15
 */
public class ParseMessage implements IParseMessage<MessageLite, Message>, LifeCycle {
    private static final Logger logger = LoggerFactory.getLogger(ParseMessage.class);

    private Map<Integer, String> numberToType = new HashMap<>();
    private Map<String, Method> returnTypeMap = new HashMap<>();
    private Map<String, Method> setMethod = new HashMap<>();

    public void parseAllMessage() {
        List<Descriptors.OneofDescriptor> oneofs = Message.getDescriptor().getOneofs();
        Map<Integer, String> numberToType = new HashMap<>();
        Map<String, Integer> typeToNumber = new HashMap<>();

        oneofs.forEach(it -> {
            it.getFields().forEach(f -> {
                String fullName = f.getMessageType().getFullName();
                int number = f.getNumber();

                logger.info("注册：[{}]->[{}]", fullName, number);
                numberToType.put(number, fullName);
                typeToNumber.put(fullName, number);
            });
        });

        this.numberToType = numberToType;

        // return
        Method[] methods = ReflectionUtils.getUniqueDeclaredMethods(Message.class);
        Map<String, Method> methodMap = new HashMap<>();
        for (Method method : methods) {
            String name = method.getReturnType().getName();
            if (typeToNumber.get(name) != null) {
                methodMap.put(name, method);
            }


        }
        this.returnTypeMap = methodMap;

        // set method
        Map<String, Method> setMethod = new HashMap<>();

        Method[] declaredMethods = Message.newBuilder().getClass().getDeclaredMethods();
        for (Method buildMethod : declaredMethods) {
            Class<?>[] parameterTypes = buildMethod.getParameterTypes();
            if (parameterTypes.length == 1) {
                String name = parameterTypes[0].getName();
                Integer num = typeToNumber.get(name);
                if (num != null) {
                    setMethod.put(name, buildMethod);
                }
            }
        }

        this.setMethod = setMethod;
    }

    @Override
    public <T> Message makeMessage(MessageLite value, Supplier<T> supplier) {
        Message.Builder message = Message.newBuilder();
        String name = value.getClass().getName();
        Method method = this.setMethod.get(name);
        try {
            method.invoke(message, value);

            Message message1 = (Message) supplier.get();
            return message.setSeq(message1.getSeq()).build();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("无法设置 " + name + e.getMessage());
        }
    }


    @Override
    public MessageLite payload(Message message) {

        Method method = this.returnTypeMap.get(path(message));
        if (method == null) {
            return message;
        }
        try {
            Object invoke = method.invoke(message);
            if (invoke instanceof MessageLite) {
                return (MessageLite) invoke;
            }
            return null;
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 消息的路由
     *
     * @return 处理消息的路由
     */
    @Override
    public String path(Message message) {
        String path = numberToType.get(message.getContentCase().getNumber());
        if (path == null) {
            return Message.class.getName();
        }
        return path;
    }


    @Override
    public void start() {
        parseAllMessage();
    }

    @Override
    public void stop() {

    }

}
