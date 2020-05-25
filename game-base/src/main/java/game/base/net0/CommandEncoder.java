package game.base.net0;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.EncoderException;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author yzz
 * 2020/3/20 11:14
 */
public class CommandEncoder extends MessageToByteEncoder<Object> {


    public CommandEncoder() {
        this(true);
    }

    public CommandEncoder(boolean preferDirect) {
        super(preferDirect);
    }


    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        if (msg instanceof Cmd) {
            Cmd command = (Cmd) msg;

            try {
                out.markWriterIndex();
                command.encode(out);
            } catch (RuntimeException e) {
                out.resetWriterIndex();
                command.completeExceptionally(new EncoderException(
                        "Cannot encode command. Please close the connection as the connection state may be out of sync.",
                        e));
            }
        }
    }
}
