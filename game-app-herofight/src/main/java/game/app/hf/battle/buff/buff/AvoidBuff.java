package game.app.hf.battle.buff.buff;

import game.app.hf.battle.buff.BaseBuff;
import game.app.hf.battle.buff.BuffData;
import game.app.hf.battle.buff.BuffType;
import game.app.hf.battle.damage.Damage;

import java.util.Map;

/**
 * @author Yunzhe.Jin
 * 2020/4/17 15:08
 */
public class AvoidBuff extends BaseBuff {


    private double limit;
    private double avoidRate;
    private double avoidRateStep;

    public AvoidBuff(BuffData buffData) {
        super(buffData);
        limit = (double) config.get("limit");
        avoidRateStep = (double) config.get("avoidRateStep");
    }


    @Override
    public BuffType type() {
        return BuffType.BUFF;
    }

    public String name() {
        return "闪避buff";
    }

    @Override
    public void beforeHurt(Damage calcDamage) {
        if (avoidRate > limit) {
            avoidRate = avoidRateStep;
        } else {
            avoidRate += avoidRateStep;
        }
        source.addFinalAvoid(avoidRate);
    }

    @Override
    public Map<String, Object> report() {
        return super.report();
    }
}
