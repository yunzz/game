package game.base.core;

import game.base.anno.NeedThreadSafe;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import proto.Message;
import proto.ServerReq;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 内部service 向网关广播
 *
 * @author Yunzhe.Jin
 * 2020/4/1 13:39
 */
@NeedThreadSafe
public class AppToGatePushMessageRoute implements PushMessageRoute {
    private ReentrantLock lock = new ReentrantLock();

    private Map<Channel, ChannelBox> channelMap = new IdentityHashMap<>();
    /**
     * 如果有多个channel 则使用最后一个连接的channel
     */
    private Map<String, ChannelBox> map = new HashMap<>();

    @Override
    public void register(ChannelHandlerContext context, Message message) {
        ServerReq serverReq = message.getServerReq();

        String serverId = serverReq.getServerId();

        lock.lock();
        try {

            ChannelBox channelBox = map.computeIfAbsent(serverId, s -> new ChannelBox());
            channelBox.addChannel(context.channel());

            channelMap.put(context.channel(), channelBox);

        } finally {
            lock.unlock();
        }

    }

    @Override
    public void push(Message message) {
        lock.lock();
        try {
            for (ChannelBox ch : map.values()) {
                ch.write(message);
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void pushAndFlush(Message message) {
        lock.lock();
        try {
            for (ChannelBox ch : map.values()) {
                ch.writeAndFlush(message);
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void onConnected(ChannelHandlerContext context) {

    }

    @Override
    public void onDisconnected(ChannelHandlerContext context) {

        lock.lock();
        try {
            ChannelBox remove = channelMap.remove(context.channel());
            if (remove != null) {
                remove.removeChannel(context.channel());
            }

        } finally {
            lock.unlock();
        }
    }

    @Override
    public void onException(ChannelHandlerContext context, Throwable throwable) {

    }

    private static class ChannelBox {
        public String name;

        public Set<Channel> channels = new HashSet<>();

        public void write(Message message) {

            for (Channel channel : channels) {
                if (channel.isActive()) {
                    channel.write(message);
                    break;
                }
            }
        }

        public void writeAndFlush(Message message) {
            for (Channel channel : channels) {
                if (channel.isActive()) {
                    channel.writeAndFlush(message);
                    break;
                }
            }
        }

        public void addChannel(Channel channel) {
            channels.add(channel);
        }

        public void removeChannel(Channel channel) {
            channels.remove(channel);
        }
    }

}
