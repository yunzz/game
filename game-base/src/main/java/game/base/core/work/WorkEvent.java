package game.base.core.work;

import io.netty.channel.Channel;
import proto.Message;

/**
 * @author Yunzhe.Jin
 * 2020/3/31 14:39
 */
public class WorkEvent {
    private Message message;
    /**
     * 推送时为空
     */
    private Channel channel;

    private Object object;

    public Message getMessage() {
        return message;
    }

    public WorkEvent setMessage(Message message) {
        this.message = message;
        return this;
    }

    public Channel getChannel() {
        return channel;
    }

    public WorkEvent setChannel(Channel channel) {
        this.channel = channel;
        return this;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    @Override
    public String toString() {
        return "WorkEvent{" +
                "message=" + message +
                ", channel=" + channel +
                ", object=" + object +
                '}';
    }
}
