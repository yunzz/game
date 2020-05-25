package game.base.handler;

import com.google.protobuf.MessageLite;
import game.base.core.MessageHandler;
import game.base.core.Player;
import proto.HbReq;
import proto.LogoutReq;
import proto.Success;

/**
 * @author Yunzhe.Jin
 * 2020/4/1 09:58
 */
public abstract class LogoutHandler implements MessageHandler<LogoutReq> {

    @Override
    public abstract MessageLite handle(Player player, LogoutReq hbReq);
}
