package game.app.hf.battle.buff.debuff;

import game.app.hf.battle.buff.BaseBuff;
import game.app.hf.battle.buff.BuffData;
import game.app.hf.battle.buff.BuffIds;
import game.app.hf.battle.buff.BuffType;
import game.app.hf.battle.damage.Damage;

/**
 * 削弱 攻击力
 *
 * @author Yunzhe.Jin
 * 2020/4/18 10:15
 */
public class WeakBuff extends BaseBuff {

    /**
     * 攻击削弱百分比
     */
    private double attack;

    /**
     * 护甲削弱百分比
     */
    private double defReduce;

    public WeakBuff(BuffData buffData) {
        super(buffData);
        attack = (double) buffData.getBuffConfig().get("attack");
        defReduce = (double) buffData.getBuffConfig().get("defReduce");
    }

    @Override
    public BuffType type() {
        return BuffType.DE_BUFF;
    }

    @Override
    public String name() {
        return "削弱";
    }

    @Override
    public int order() {
        return 0;
    }

    @Override
    public void beforeAction() {

        // 减攻
        if (attack > 0) {
            target.decFinalDamage(attack);
        }
    }

    @Override
    public void beforeHurt(Damage calcDamage) {
        // 减甲
        if (defReduce > 0) {
            target.decFinalDef(defReduce);
        }
    }

    @Override
    public int buffId() {
        return BuffIds.WEEK_DE_BUFF;
    }


    public double getAttack() {
        return attack;
    }

    public void setAttack(double attack) {
        this.attack = attack;
    }

    public double getDefReduce() {
        return defReduce;
    }

    public void setDefReduce(double defReduce) {
        this.defReduce = defReduce;
    }
}
