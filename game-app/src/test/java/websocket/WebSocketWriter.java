package websocket;

import game.base.core.Messages;
import game.base.log.Logs;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import proto.Message;

/**
 * @author Yunzhe.Jin
 * 2020/5/14 20:25
 */
public class WebSocketWriter extends ChannelOutboundHandlerAdapter {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        WriteMessage message = (WriteMessage) msg;
        Logs.common.info("写消息：" + message);
        // 不需要回复的消息


        WebSocketFrame frame = new BinaryWebSocketFrame(Unpooled.wrappedBuffer(message.getMessage()));
        super.write(ctx, frame, promise);
    }


}
