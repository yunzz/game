package game.gateway.module;

import game.base.core.Game;
import game.base.core.MessageHandler;
import game.base.core.Player;
import game.base.net.client.ClientType;
import game.base.net.client.TcpClient;
import proto.Message;

import java.util.concurrent.CompletableFuture;

/**
 * 全局消息转发
 *
 * @author Yunzhe.Jin
 * 2020/4/3 14:25
 */
public class MessageProcessHandler implements MessageHandler<Message> {

    @Override
    public Void handle(Player player, Message message) {
        // 前端传过来的seq，需要保存起来。
        final long seq = message.getSeq();
        Message msg = Message.newBuilder(message).setUid(player.id()).build();
        // 转发到世界服
        TcpClient userClient = Game.getTcpClientManager().getUserClient(player.id(), ClientType.WORLD.type());
        CompletableFuture<Message> future = userClient.sendFlush(msg);
        future.whenComplete((message1, throwable) -> {
            // 推送给用户
            Message retMsg = Message.newBuilder(message1).clearUid().clearBroadcastUid().setSeq(seq).build();
            player.getChannel().sendFlush(retMsg);
        });
        return null;
    }

}
