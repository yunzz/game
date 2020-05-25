package game.base.net0;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.EncoderException;

/**
 * @author yzz
 * 2020/3/20 10:07
 */
public interface Cmd {
    void encode(ByteBuf out);

    void completeExceptionally(EncoderException e);
}
