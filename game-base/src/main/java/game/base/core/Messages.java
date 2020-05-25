package game.base.core;

import proto.Error;
import proto.Message;
import proto.Success;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author Yunzhe.Jin
 * 2020/3/31 14:40
 */
public class Messages {

    public static final Success SUCCESS = Success.newBuilder().build();

    public static final Function<Long, Message> SUCCESS_MSG = (seq) -> Message.newBuilder().setSeq(seq).setSuccessRes(SUCCESS).build();

    public static final BiFunction<Long, Integer, Message> ERROR_MSG = (seq, index) ->
            Message.newBuilder().setSeq(seq).setErrorRes(Error.newBuilder().setIndex(index).build()).build();


    /**
     * 是否为心跳
     */
    public static boolean isHeartbeat(Message message) {
        return message.getContentCase() == Message.ContentCase.HBREQ;
    }

    public static boolean isLogin(Message message) {
        return message.getContentCase() == Message.ContentCase.LOGINREQ;
    }

    /**
     * 是否为向gateway推送信息
     */
    public static boolean isPushMessage(Message message) {
        return message.getContentCase().name().toLowerCase().endsWith("msg");
    }

    /**
     * 是否为请求消息
     */
    public static boolean isRequestMessage(Message message) {
        return message.getContentCase().name().toLowerCase().endsWith("req");
    }

    /**
     * 是否为响应消息
     */
    public static boolean isResponseMessage(Message message) {
        return message.getContentCase().name().toLowerCase().endsWith("res");
    }

    /**
     * 是否为 tel消息， 不需要进行回复的
     *
     * @param message
     * @return
     */
    public static boolean isTellMessage(Message message) {
        return message.getContentCase().name().toLowerCase().endsWith("tel");
    }


    /**
     * 服务器内部请求消息
     */
    public static boolean isServerMessage(Message message) {
        return message.getContentCase().name().toLowerCase().endsWith("serverreq");
    }
}
