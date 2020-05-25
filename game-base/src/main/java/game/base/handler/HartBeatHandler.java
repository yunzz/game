package game.base.handler;

import game.base.core.MessageHandler;
import game.base.core.Player;
import proto.HbReq;
import proto.HbRes;

import java.time.ZoneId;
import java.util.Date;

/**
 * 心跳
 *
 * @author Yunzhe.Jin
 * 2020/3/27 17:51
 */
public class HartBeatHandler implements MessageHandler<HbReq> {

    @Override
    public HbRes handle(Player player, HbReq hbReq) {
        return HbRes.newBuilder()
                .setTime(new Date().getTime())
                .setZone(ZoneId.systemDefault().getId())
                .build();
    }
}
