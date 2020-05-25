package game.base.core.work;

import com.google.protobuf.MessageLite;
import game.base.core.*;
import game.base.core.error.ErrorEnum;
import io.netty.channel.Channel;
import proto.LoginRes;
import proto.Message;

import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Yunzhe.Jin
 * 2020/4/4 21:05
 */
public class UserLoginWorkHandler extends WorkHandler {

    private Set<Channel> channels = ConcurrentHashMap.newKeySet();
    private Set<Long> ids = ConcurrentHashMap.newKeySet();

    @Override
    public void doEvent(WorkEvent event) throws Exception {

        final Channel channel = event.getChannel();
        Message msg = event.getMessage();
        IParseMessage<MessageLite, Message> parseMessage = Game.server.getParseMessage();
        // check uid
        final long uid = msg.getUid();

        MessageLite payload = parseMessage.payload(msg);
        MessageHandler<MessageLite> process = process(msg);
        if (uid == 0) {
            // 不带uid登录
            if (channels.contains(channel)) {
                //重复请求登录
                sendError(msg.getSeq(), channel, ErrorEnum.ERR_USER_LOGIN);
                return;
            } else {
                channels.add(channel);
            }

            Player player = new Player();
            player.setChannel(new PlayerChannel(channel));
            // 调用登录接口
            CompletableFuture<Player> res = (CompletableFuture<Player>) process.handle(player, payload);
            res.whenComplete((p, throwable) -> { //  这里的代码是在其他线程执行的

                Game.addPlayer(player.id(), event.getChannel());
                channels.remove(channel);
                if (throwable != null) {

                    Game.removePlayer(player.id());
                    sendError(msg, channel, throwable.getCause());
                    return;
                }
                p.getChannel().setChannelUid(p.id());
                // 发送给用户所在的work
                Game.getWorkManager().innerSendToUser(Message.newBuilder().setUid(p.id()).setSuccessRes(Messages.SUCCESS).build(), channel, p);
                sendMsg(Message.newBuilder().setSeq(msg.getSeq()).setLoginRes(LoginRes.newBuilder().build()).build(), channel, msg);
            });
        } else {
            // 带uid登录
            if (ids.contains(uid)) {
                //重复请求登录
                sendError(msg.getSeq(), channel, ErrorEnum.ERR_USER_IN_LOGIN);
                return;
            } else {
                if (Game.getPlayer(uid) != null) {
                    sendError(msg.getSeq(), channel, ErrorEnum.ERR_USER_LOGIN);
                    return;
                }
                ids.add(uid);
            }

            Player player = new Player();
            player.setChannel(new PlayerChannel(channel));
            player.setId(uid);
            // 调用登录接口
            CompletableFuture<Player> res = (CompletableFuture<Player>) process.handle(player, payload);
            res.whenComplete((p, throwable) -> { //  这里的代码是在其他线程执行的
                Game.addPlayer(player.id(), event.getChannel());
                ids.remove(uid);
                if (throwable != null) {
                    Game.removePlayer(player.id());
                    sendError(msg, channel, throwable);
                    return;
                }
                // 用id进行登录
                // 发送给用户所在的work
                Game.getWorkManager().innerSendToUser(Message.newBuilder().setUid(p.id()).setSuccessRes(Messages.SUCCESS).build(), channel, p);
                sendMsg(LoginRes.newBuilder().build(), channel, msg);
            });
        }

    }
}
