package game.app.hf.battle.heroes;

import game.app.hf.battle.FightHero;
import game.app.hf.battle.buff.BuffData;
import game.app.hf.battle.buff.BuffIds;
import game.app.hf.battle.buff.buff.AvoidBuff;
import game.app.hf.battle.damage.Damage;
import game.app.hf.battle.damage.NormalAttackDamage;
import game.app.hf.battle.skill.ContinueAttackSkill;

import java.util.HashMap;
import java.util.Map;

/**
 * 赵云
 *
 * @author Yunzhe.Jin
 * 2020/4/17 15:13
 */
public class ZhaoYunFightHero extends FightHero {

    private double limit = 0.4;
    private double avoidRate;
    private double avoidRateStep = 0.1;


    @Override
    public void beforeStart() {


        Map<String, Object> config = new HashMap<>(3);
        config.put("limit", 0.4);
        config.put("avoidRateStep", 0.1);

        BuffData buffData = BuffData.builder()
                .setSource(this).setTarget(this).setRound(2)
                .setBuffId(102)
                .setBuffConfig(config)
                .build();

        // 闪避buff
        AvoidBuff avoidBuff = new AvoidBuff(buffData);
        addBuff(this, avoidBuff);

    }

    @Override
    public void beforeAction() {
        super.beforeAction();

    }

    @Override
    public void action() {
        FightHero enemy = fightScene.getEnemy(getSide());

        attack(enemy, new NormalAttackDamage(this, enemy, finalAttackDamage));
        // 攻击2次
        ContinueAttackSkill skill = new ContinueAttackSkill(0.2, enemy);
        skill.action(this);
    }

    @Override
    protected void beforeHurt(Damage damage) {
        if (avoidRate > limit) {
            avoidRate = avoidRateStep;
        } else {
            avoidRate += avoidRateStep;
        }
        finalAvoid *= (1 + avoidRate);
    }
}
