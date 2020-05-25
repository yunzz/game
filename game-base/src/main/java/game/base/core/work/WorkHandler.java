package game.base.core.work;

import com.google.protobuf.MessageLite;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.LifecycleAware;
import game.base.core.*;
import game.base.core.error.ErrorEnum;
import game.base.core.error.ModuleAssert;
import game.base.core.error.ModuleErrorNoResolve;
import game.base.core.error.ModuleException;
import game.base.log.Logs;
import io.netty.channel.Channel;
import proto.Error;
import proto.Message;
import proto.Success;

import java.util.concurrent.CompletableFuture;

/**
 * 玩家业务处理
 * 这个handler是 单线程同一个线程中处理
 *
 * @author Yunzhe.Jin
 * 2020/3/31 14:36
 */
public class WorkHandler implements EventHandler<WorkEvent>, LifecycleAware {

    public WorkHandler() {

    }

    @Override
    public final void onEvent(WorkEvent event, long sequence, boolean endOfBatch) throws Exception {

        final Channel channel = event.getChannel();
        Message msg = event.getMessage();
        try {
            Logs.common.debug("处理消息：{}", event);
            doEvent(event);
        } catch (ModuleException e) {
            Logs.common.error("{}", e.toString(), e);
            sendError(msg.getSeq(), channel, e.getErrorNo());
        } catch (Throwable e) {
            Logs.common.error("", e);
            sendError(msg.getSeq(), channel, ErrorEnum.ERR_1);
        }
    }

    protected void doEvent(WorkEvent event) throws Exception {

    }

    protected MessageHandler<MessageLite> process(Message msg) {
        IParseMessage<MessageLite, Message> parseMessage = Game.server.getParseMessage();
        String path = parseMessage.path(msg);
        Handlers handler = Game.server.getHandler();
        MessageHandler<MessageLite> process = handler.getHandler(path);
        ModuleAssert.notNull(process, ErrorEnum.ERR_404, path);
        return process;
    }

    protected void doProcess(Message msg, Channel channel, Player player) {

        IParseMessage<MessageLite, Message> parseMessage = Game.server.getParseMessage();

        String path = parseMessage.path(msg);
        Handlers handler = Game.server.getHandler();
        Object res;
        Logs.common.debug("消息path: {}", path);
        if (handler.isUseTopMessageHandler(path)) {

            MessageHandler<MessageLite> process = handler.getMessageHandler();
            res = process.handle(player, msg);
        } else {
            MessageHandler<MessageLite> process = handler.getHandler(path);
            MessageLite payload = parseMessage.payload(msg);
            res = process.handle(player, payload);
        }
        sendMsg(res, channel, msg);
    }

    protected void sendMsg(Object res, Channel channel, Message originMsg) {
        IParseMessage<MessageLite, Message> parseMessage = Game.server.getParseMessage();
        if (res == null || res instanceof Void) {
            // 返回成功
            channel.writeAndFlush(Messages.SUCCESS_MSG.apply(originMsg.getSeq()));
        } else if (res instanceof Message) {
            channel.writeAndFlush(res);
        } else if (res instanceof MessageLite) {
            Message returnMsg = parseMessage.makeMessage((MessageLite) res, () -> originMsg);
            channel.writeAndFlush(returnMsg);
        } else if (res instanceof CompletableFuture) {
            CompletableFuture<?> r = (CompletableFuture<?>) res;
            r.whenComplete((o, throwable) -> {
                if (throwable != null) {
                    sendError(originMsg, channel, throwable.getCause());
                } else if (!(o instanceof Void)) {

                    if (o instanceof MessageLite) {
                        Message returnMsg = parseMessage.makeMessage((MessageLite) o, () -> originMsg);
                        channel.writeAndFlush(returnMsg);
                    } else {
                        Logs.common.error("{}", o.toString());
                        sendError(originMsg.getSeq(), channel, ErrorEnum.ERR_TYPE);
                    }
                }

            });
        }
    }

    protected void sendError(Message msg, Channel channel, Throwable throwable) {
        Logs.common.error("发生异常", throwable);
        if (throwable instanceof ModuleException) {
            sendError(msg.getSeq(), channel, ((ModuleException) throwable).getErrorNo());
        } else {
            sendError(msg.getSeq(), channel, ErrorEnum.ERR_1);
        }
    }

    protected void sendError(long seq, Channel channel, ModuleErrorNoResolve r) {

        Logs.common.error("发生错误：{}", r.display());
        Message m = Message.newBuilder()
                .setSeq(seq)
                .setErrorRes(Error.newBuilder()
                        .setIndex(r.errorNo())
                        .build()).build();
        channel.writeAndFlush(m);
    }


    @Override
    public void onStart() {
        System.out.println("启动 WorkHandler");
    }

    @Override
    public void onShutdown() {
        System.out.println("关闭 WorkHandler-");
    }

    private enum WorkStat {
        LOGIN,
    }
}