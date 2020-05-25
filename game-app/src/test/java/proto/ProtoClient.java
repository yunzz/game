package proto;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Random;

/**
 * @author Yunzhe.Jin
 * 2020/3/26 20:19
 */
public class ProtoClient {

    public static void main(String[] args) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup(1);
        Bootstrap b = new Bootstrap();
        b.group(group)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        ChannelPipeline p = ch.pipeline();
                        p.addLast(new WorldClockClientInitializer());
                    }
                });

        Channel ch = b.connect("127.0.0.1", 8000).sync().channel();
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        String client = "";
        long seq = 1;
        while (true) {
            try {
                String msg = console.readLine();
                String[] split = msg.split("\\s+");
                if (split[0].startsWith("c")) {
                    client = msg;
                    continue;
                }
                Message message = null;

                switch (split[0]) {
                    case "login":
                        message = Message.newBuilder()
                                .setSeq(seq++)
                                .setUid(11)
                                .setLoginReq(Message.newBuilder().getLoginReqBuilder()
                                        .build())
                                .build();

                        ch.writeAndFlush(message);

                        break;
                    case "move":
                        for (int i = 0; i < Integer.parseInt(split[1]); i++) {

                            message = Message.newBuilder()
                                    .setSeq(seq++)
                                    .setMoveTel(Message.newBuilder().getMoveTelBuilder()
                                            .setX(new Date().getTime())
                                            .setY(new Date().getTime())
                                            .build())
                                    .build();

                            ch.writeAndFlush(message);
                        }

                        break;
                    case "2":
                        for (int i = 0; i < 1000; i++) {

                            Message message2 = Message.newBuilder()
                                    .setSeq(seq++)
                                    .setErrorRes(Message.newBuilder().getErrorResBuilder()
                                            .setContent(client)
                                            .setIndex(6)
                                            .build())
                                    .build();

                            ch.writeAndFlush(message2);
                        }

                    case "room1":
                        message = Message.newBuilder()
                                .setSeq(seq++)
                                .setCrateRoomReq(Message.newBuilder().getCrateRoomReq())
                                .build();
                        ch.writeAndFlush(message);
                        break;
                    case "roomIn":
                        message = Message.newBuilder()
                                .setSeq(seq++)
                                .setJoinRoomReq(Message.newBuilder().getJoinRoomReqBuilder().setRoomId(
                                        Integer.parseInt(split[1])
                                ).build())
                                .build();
                        ch.writeAndFlush(message);
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
