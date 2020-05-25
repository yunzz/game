package game.app.hf.handlers.battle;

import game.base.core.MessageHandler;
import game.base.core.Player;
import proto.BattleFightReq;
import proto.BattleFightRes;

/**
 * @author Yunzhe.Jin
 * 2020/4/3 17:28
 */
public class FightHandler implements MessageHandler<BattleFightReq> {

    @Override
    public BattleFightRes handle(Player player, BattleFightReq moveTel) {


        return null;
    }
}
