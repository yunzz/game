package game.client.fs;

/**
 * @author yunzz
 */

import com.google.protobuf.MessageLite;
import game.base.http.GameHttpClient;
import game.base.uitools.event.EventManager;
import game.base.uitools.event.events.ConsoleEvent;
import game.base.utils.JsonUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.*;

import java.net.URI;
import java.nio.ByteBuffer;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import proto.*;

public final class WebSocketClient {
    public static WebSocketClient Instance = new WebSocketClient();

    public String URL = "";
    Channel finalCh;
    private ChannelFuture connect;
    private String host;
    private String sub = "&appNo=3&token=a";
    private EventLoopGroup group;
    public long uid;


    public WebSocketClient() {
    }

    public void connect(long uid) throws Exception {
        this.uid = uid;

        String s = GameHttpClient.get(null, "http://获取服务器列表");

        EventManager.fireEvent(new ConsoleEvent("获取地址：" + s));
        LinkedHashMap linkedHashMap = JsonUtil.fromJsonString(s, LinkedHashMap.class);
        Map result = (Map) linkedHashMap.get("result");
        this.host = result.get("host").toString();
        EventManager.fireEvent(new ConsoleEvent("Host:" + this.host));

        this.URL = this.host + "?uid=" +uid + sub;

        System.out.println(URL);

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

        group = new NioEventLoopGroup();
        final WebSocketClientHandler handler =
                new WebSocketClientHandler(
                        WebSocketClientHandshakerFactory.newHandshaker(
                                uri, WebSocketVersion.V13, null, false, new DefaultHttpHeaders()));

        Bootstrap b = new Bootstrap();
        b.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)

                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        ChannelPipeline p = ch.pipeline();
                        p.addLast(
//                                    new LoggingHandler(),
                                new HttpClientCodec(),
                                new HttpObjectAggregator(8192),
                                handler
                        );
                    }
                });

        this.connect = b.connect(uri.getHost(), port);
        Channel ch = connect.sync().channel();

        finalCh = ch;
        new Thread(() -> {
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                GatewayMessage msg = new GatewayMessage((byte) 1, 3, (byte) 0, null);
                WebSocketFrame frame = new BinaryWebSocketFrame(Unpooled.wrappedBuffer(msg.getAllBytes()));
                finalCh.writeAndFlush(msg(3, null));
            }
        }).start();
        String msg = "";
        if (!ch.isActive()) {
            System.out.println("重连");
            ch = connect.sync().channel();
            handler.handshakeFuture().sync();
        }
        System.out.println("命令:");
        if (msg == null) {
        } else if ("bye".equals(msg.toLowerCase())) {
            ch.writeAndFlush(new CloseWebSocketFrame());
            ch.closeFuture().sync();
        } else if ("hb".equals(msg.toLowerCase())) {

            GatewayMessage msg1 = new GatewayMessage((byte) 1, 3, (byte) 0, null);
            WebSocketFrame frame = new BinaryWebSocketFrame(Unpooled.wrappedBuffer(msg1.getAllBytes()));
            finalCh.writeAndFlush(frame);

        } else if ("item".equals(msg.toLowerCase())) {
        } else if ("close".equals(msg.toLowerCase())) {
            ch.close();
        } else if ("1".equals(msg.toLowerCase())) {

        } else if ("s".equals(msg.toLowerCase())) {
            finalCh.writeAndFlush(msg(200, FsGameStartReq.newBuilder().setRoomId(1).build()));

        }

    }

    public void startGame() {
        finalCh.writeAndFlush(msg(200, FsGameStartReq.newBuilder().setRoomId(1).build()));
    }

    public void send(int msg, MessageLite messageLite) {
        System.out.println("--------->send");
        System.out.println(messageLite);
        finalCh.writeAndFlush(msg(msg, messageLite));
    }


    public void sendMatch(int msg, MessageLite messageLite) {
        System.out.println("--------->send match");
        System.out.println(messageLite);
        finalCh.writeAndFlush(matchMsg(msg, messageLite));
    }

    public Channel getCh() {
        return this.finalCh;
    }

    private static BinaryWebSocketFrame msg(int msgNo, MessageLite msg) {
        final int sno = 13;
        final int apNo = 3;

        int gateWayMsgNo = apNo * 100;
        gateWayMsgNo += sno;
        gateWayMsgNo *= 1000;
        gateWayMsgNo += msgNo;
        GatewayMessage msg1 = new GatewayMessage((byte) 1, gateWayMsgNo, (byte) 0, msg == null ? null : ByteBuffer.wrap(msg.toByteArray()));
        return new BinaryWebSocketFrame(Unpooled.wrappedBuffer(msg1.getAllBytes()));
    }

    private static BinaryWebSocketFrame matchMsg(int msgNo, MessageLite msg) {
        final int sno = 2;
        final int apNo = 3;

        int gateWayMsgNo = apNo * 100;
        gateWayMsgNo += sno;
        gateWayMsgNo *= 1000;
        gateWayMsgNo += msgNo;
        GatewayMessage msg1 = new GatewayMessage((byte) 1, gateWayMsgNo, (byte) 0, msg == null ? null : ByteBuffer.wrap(msg.toByteArray()));
        return new BinaryWebSocketFrame(Unpooled.wrappedBuffer(msg1.getAllBytes()));
    }

    public ChannelFuture getConnect() {
        return this.connect;
    }

    public void stop() {
        group.shutdownGracefully();
    }
}
