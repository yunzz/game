import game.client.fs.GatewayMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;

import java.util.List;

/**
 * Create by yuruyi on 2017/11/06
 * 解码器
 */
@ChannelHandler.Sharable
public class WebSocketDecoder extends MessageToMessageDecoder<BinaryWebSocketFrame> {

    @Override
    protected void decode(ChannelHandlerContext ctx, BinaryWebSocketFrame frame, List<Object> out) {
        ByteBuf byteBuf = frame.content();

        out.add(new GatewayMessage(byteBuf));
    }
}