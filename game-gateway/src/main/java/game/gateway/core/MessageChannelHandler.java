package game.gateway.core;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import proto.Message;

/**
 * @author Yunzhe.Jin
 * 2020/4/4 22:10
 */
@ChannelHandler.Sharable
public class MessageChannelHandler extends SimpleChannelInboundHandler<Message> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
        if (msg.getUid() != 0) {
            // 去掉uid, 网关不需要用户的uid
            msg = Message.newBuilder(msg).setUid(0).build();
        }
        ctx.fireChannelRead(msg);
    }
}
