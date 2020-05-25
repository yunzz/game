package game.app.hf.battle.heroes;

import game.app.hf.battle.FightHero;
import game.app.hf.battle.buff.BuffData;
import game.app.hf.battle.buff.BuffIds;
import game.app.hf.battle.buff.debuff.WeakBuff;
import game.app.hf.battle.damage.NormalAttackDamage;
import game.base.log.Logs;

import java.util.HashMap;
import java.util.Map;

/**
 * 张飞
 *
 * @author Yunzhe.Jin
 * 2020/4/18 10:13
 */
public class ZhangFeiFightHero extends FightHero {

    @Override
    public void action() {
        FightHero enemy = fightScene.getEnemy(getSide());
        if (enemy == null) {
            return;
        }

        BuffData.Builder builder = BuffData.builder();
        // todo from config
        Map<String, Object> config = new HashMap<>();
        config.put("id", BuffIds.WEEK_DE_BUFF);
        config.put("round", 2);
        config.put("attack", 0.2);
        config.put("defReduce", 0.2);

        BuffData data = builder
                .setBuffId(1002)
                .setSource(this)
                .setTarget(enemy)
                .setBuffConfig(config).build();
        WeakBuff weakBuff = new WeakBuff(data);
        boolean b = enemy.addBuff(this, weakBuff);
        if (!b) {
            Logs.common.debug("添加buff失败");
        }

        attack(enemy, new NormalAttackDamage(this, enemy, finalAttackDamage));

    }
}
