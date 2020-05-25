package game.base.net.server;

import com.google.protobuf.MessageLite;
import game.base.core.*;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import proto.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yunzhe.Jin
 * 2020/3/26 21:59
 */
public class WebsocketServerHandler {
    private static final Logger log = LoggerFactory.getLogger(WebsocketServerHandler.class);

    private static final String WEBSOCKET_PATH = "/ws";

    public static List<ChannelHandler> handlers() {
        List<ChannelHandler> list = new ArrayList<>();
        list.add(new HttpServerCodec()); // in out
        list.add(new HttpObjectAggregator(65536)); // in
        list.add(new WebSocketServerCompressionHandler());// in out
        list.add(new WebSocketServerProtocolHandler(WEBSOCKET_PATH, null, true));// in
        list.add(new WebSocketFrameHandler()); // in
        list.add(new ProtobufWsDecode()); // in
        list.add(new ProtobufWsEncode()); // out
        list.add(new WebSocketFlowLogHandler()); // in
        list.add(new WebSocketMessageInbound()); // in
        return list;
    }

    private static class WebSocketFrameHandler extends ChannelInboundHandlerAdapter {

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object frame) {
            if (frame instanceof BinaryWebSocketFrame) {
                ctx.fireChannelRead(((BinaryWebSocketFrame) frame).content());
            } else {
                String message = "unsupported frame type: " + frame.getClass().getName();
                throw new UnsupportedOperationException(message);
            }
        }
    }

    private static class WebSocketFlowLogHandler extends ChannelDuplexHandler {
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            super.channelRead(ctx, msg);
        }

        @Override
        public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
            super.write(ctx, msg, promise);
        }
    }

    /**
     * 消息转发给处理业务
     */
    private static class WebSocketMessageInbound extends SimpleChannelInboundHandler<Message> {
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            log.info("WebSocketMessageInbound channelActive");
            super.channelActive(ctx);
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            log.info("WebSocketMessageInbound channelInactive");
            super.channelInactive(ctx);
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
            log.info("WebSocketMessageInbound channelRead0:{}", msg);

            IParseMessage<MessageLite, Message> parseMessage = Game.server.getParseMessage();
            String path = parseMessage.path(msg);
            MessageLite payload = parseMessage.payload(msg);
            Handlers handler = Game.server.getHandler();
            MessageHandler<MessageLite> process = handler.getHandler(path);
            if (process == null) {
                log.error("没有找到handler:{}", path);
            } else {
                MessageLite res = (MessageLite) process.handle(null, payload);
                Message returnMsg = parseMessage.makeMessage(res, () -> msg);
                ctx.channel().writeAndFlush(returnMsg);
            }
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            super.exceptionCaught(ctx, cause);
        }

        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        }
    }

    private static class ProtobufWsEncode extends MessageToMessageEncoder<Message> {

        @Override
        protected void encode(ChannelHandlerContext ctx, Message msg, List<Object> out) throws Exception {
            out.add(new BinaryWebSocketFrame(Unpooled.wrappedBuffer(msg.toByteArray())));
        }
    }

    private static class ProtobufWsDecode extends MessageToMessageDecoder<ByteBuf> {
        private final MessageLite prototype = Message.getDefaultInstance();

        @Override
        protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {

            final byte[] array;
            final int offset;
            final int length = msg.readableBytes();
            if (msg.hasArray()) {
                array = msg.array();
                offset = msg.arrayOffset() + msg.readerIndex();
            } else {
                array = ByteBufUtil.getBytes(msg, msg.readerIndex(), length, false);
                offset = 0;
            }

            out.add(prototype.getParserForType().parseFrom(array, offset, length));
        }
    }

}
