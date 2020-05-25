package game.base.core;

import game.base.core.error.ErrorEnum;
import game.base.core.error.ModuleAssert;
import game.base.module.room.MsgRoomInner;
import io.netty.channel.Channel;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import proto.Message;

/**
 * @author Yunzhe.Jin
 * 2020/4/1 10:10
 */
public class PlayerChannel {
    private Channel channel;
    public static final AttributeKey<Long> uidKey = AttributeKey.newInstance("uid");

    public PlayerChannel(Channel channel) {
        this.channel = channel;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public void push(Message message) {
        ModuleAssert.isTrue(Messages.isPushMessage(message), ErrorEnum.ERR_NOT_PUSH_MSG);
        if (channel != null && channel.isActive() && channel.isWritable()) {
            channel.writeAndFlush(message);
        }
    }

    public void send(Message message) {
        channel.write(message);
    }

    public void sendFlush(Message message) {
        channel.writeAndFlush(message);
    }

    public void setChannelUid(long uid) {
        Attribute<Long> attr = channel.attr(uidKey);
        attr.set(uid);
    }

    @Override
    public String toString() {
        return "PlayerChannel{" +
                "channel=" + channel +
                '}';
    }
}
