package game.client.net;

import game.base.core.ParseMessage;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import proto.Message;

/**
 * @author Yunzhe.Jin
 * 2020/4/7 14:56
 */
public class Client {
    private EventLoopGroup group;
    private Channel ch;
    private ChannelFuture connect;
    public final static Client instance = new Client();
    private ClientHandlers handlers = new ClientHandlers();
    private ParseMessage parseMessage = new ParseMessage();

    public Client() {
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void connect(String host, int port) throws InterruptedException {
        group = new NioEventLoopGroup(1);
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
        connect = b.connect(host, port);
        ch = connect.sync().channel();
    }

    public ChannelFuture getConnect() {
        return connect;
    }

    public ClientHandlers getHandlers() {
        return handlers;
    }

    public void start() {
        parseMessage.parseAllMessage();
        registerHandler();
    }

    public void stop() {
        if (group != null) {
            group.shutdownGracefully();
        }
    }

    public Channel getCh() {
        return ch;
    }

    public void registerHandler() {
//        handlers.registerHandler(new PutKeyMessageHandler());
//        handlers.registerHandler(new EnterRoomFvstResHandler());
//        handlers.registerHandler(new StartFvstMsgHandler());
//        handlers.registerHandler(new PutKeyFvstMsgHandler());
//        handlers.registerHandler(new ErrmsgHandler());
//        handlers.registerHandler(new FindRoomResHandler());
//        handlers.registerHandler(new GameEndFvstMsgHandler());
    }

    public void send(Message message) {
        ch.writeAndFlush(message);
    }

    public ParseMessage getParseMessage() {
        return parseMessage;
    }
}
