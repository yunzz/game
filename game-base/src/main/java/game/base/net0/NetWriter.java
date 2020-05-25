package game.base.net0;

import io.netty.channel.Channel;

import java.util.concurrent.CompletableFuture;

/**
 * @author yzz
 * 2020/3/20 10:07
 */
public interface NetWriter {

    Cmd write(Cmd cmd);

    void flush();

    void close();

    CompletableFuture<Void> closeAsync();

    void notifyException(Throwable cause);

    void notifyChannelActive(Channel channel);

    void notifyChannelInactive(Channel channel);
}

