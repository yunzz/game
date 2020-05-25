package websocket;

/**
 * @author yunzz
 */

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.*;
import proto.Message;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;


public final class WebSocketClient {

    static final String URL = System.getProperty("url", "ws://172.17.18.131:8888/g?uid=1835212091100012&appNo=3&token=a");

    public static void main(String[] args) throws Exception {
        URI uri = new URI(URL);
        String scheme = uri.getScheme() == null ? "ws" : uri.getScheme();
        final int port;
        if (uri.getPort() == -1) {
            if ("ws".equalsIgnoreCase(scheme)) {
                port = 80;
            } else if ("wss".equalsIgnoreCase(scheme)) {
                port = 443;
            } else {
                port = -1;
            }
        } else {
            port = uri.getPort();
        }

        if (!"ws".equalsIgnoreCase(scheme) && !"wss".equalsIgnoreCase(scheme)) {
            System.err.println("Only WS is supported.");
            return;
        }

        EventLoopGroup group = new NioEventLoopGroup();
        try {
            final WebSocketClientHandler handler =
                    new WebSocketClientHandler(
                            WebSocketClientHandshakerFactory.newHandshaker(
                                    uri, WebSocketVersion.V13, null, false, new DefaultHttpHeaders()));

            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ChannelPipeline p = ch.pipeline();
                            p.addLast(
                                    new HttpClientCodec(),
                                    new HttpObjectAggregator(8192),
                                    handler);
                        }
                    });

            Channel ch = b.connect(uri.getHost(), port).sync().channel();
            handler.handshakeFuture().sync();

            BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                if (!ch.isActive()) {
                    System.out.println("重连");
                    ch = b.connect(uri.getHost(), port).sync().channel();
                    handler.handshakeFuture().sync();
                }
                String msg = console.readLine();
                if (msg == null) {
                    break;
                } else if ("bye".equals(msg.toLowerCase())) {
                    ch.writeAndFlush(new CloseWebSocketFrame());
                    ch.closeFuture().sync();
                    break;
                } else if ("ping".equals(msg.toLowerCase())) {
                    WebSocketFrame frame = new PingWebSocketFrame(Unpooled.wrappedBuffer(new byte[]{8, 1, 8, 1}));
                    ch.writeAndFlush(frame);
                } else if ("login".equals(msg.toLowerCase())) {

                } else if ("item".equals(msg.toLowerCase())) {
                } else if ("close".equals(msg.toLowerCase())) {
                    ch.close();
                } else if ("1".equals(msg.toLowerCase())) {
                    Message message = Message.newBuilder()
                            .setSeq(1)
                            .setSuccessRes(Message.newBuilder().getSuccessResBuilder().setOk("ooook").build())
                            .build();

                    WebSocketFrame frame = new BinaryWebSocketFrame(Unpooled.wrappedBuffer(message.toByteArray()));
                    ch.writeAndFlush(frame);
                }
                switch (msg) {
                    case "2":
                        for (int i = 0; i < 1; i++) {

                            Message message = Message.newBuilder()
                                    .setSeq(1)
                                    .setErrorRes(Message.newBuilder().getErrorResBuilder()
                                            .setContent("sss")
                                            .setIndex(6)
                                            .build())
                                    .build();

                            WebSocketFrame frame = new BinaryWebSocketFrame(Unpooled.wrappedBuffer(message.toByteArray()));
                            ch.writeAndFlush(frame);
                        }
                        break;
                }


            }
        } finally {
            group.shutdownGracefully();
        }
    }
}
