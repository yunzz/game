import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;

import java.util.List;

/**
 * Create by yuruyi on 2017/11/06
 * 编码器
 */
@ChannelHandler.Sharable
public class WebSocketEncoder extends MessageToMessageEncoder<byte[]> {

    @Override
    protected void encode(ChannelHandlerContext ctx, byte[] bytes, List<Object> list) {
        ByteBuf byteBuf = Unpooled.wrappedBuffer(bytes);
        BinaryWebSocketFrame frame = new BinaryWebSocketFrame(byteBuf);
        list.add(frame);
    }
}
