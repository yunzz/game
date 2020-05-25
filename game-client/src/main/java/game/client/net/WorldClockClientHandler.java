package game.client.net;

import com.google.protobuf.MessageLite;
import game.base.core.ParseMessage;
import game.base.log.Logs;
import game.base.uitools.event.EventManager;
import game.base.uitools.event.events.ConsoleEvent;
import game.client.ClientMessageHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import javafx.application.Platform;
import org.apache.commons.compress.archivers.sevenz.CLI;
import proto.Message;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.regex.Pattern;

/**
 * @author Yunzhe.Jin
 * 2020/3/26 20:20
 */

public class WorldClockClientHandler extends SimpleChannelInboundHandler<Message> {

    private static final Pattern DELIM = Pattern.compile("/");

    // Stateful properties
    private volatile Channel channel;
    private final BlockingQueue<Message> answer = new LinkedBlockingQueue<Message>();

    public WorldClockClientHandler() {
        super(false);
    }


    @Override
    public void channelRegistered(ChannelHandlerContext ctx) {
        channel = ctx.channel();
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
        EventManager.fireEvent(new ConsoleEvent(ctx.channel() + ":\n" + msg.toString()));
        ParseMessage parseMessage = Client.instance.getParseMessage();

        String path = parseMessage.path(msg);
        Logs.common.debug("path:{}", path);
        ClientHandlers handlers = Client.instance.getHandlers();
        ClientMessageHandler<MessageLite> handler = handlers.getHandler(path);
        if (handler == null) {
            Logs.common.warn("handler不存在:{}", path);
        } else {
            Platform.runLater(() -> {
                handler.handle(null, parseMessage.payload(msg));
            });
        }

        System.out.println("客户端收到消息：\n" + msg);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
