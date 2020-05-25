package game.app.hf.battle.heroes;

import game.app.hf.battle.FightHero;
import game.app.hf.battle.damage.NormalAttackDamage;
import game.app.hf.battle.skill.RangeAttackSkill;

/**
 * 关羽
 *
 * @author Yunzhe.Jin
 * 2020/4/17 15:41
 */
public class GuanYuFightHero extends FightHero {
    /**
     * 最后一次攻击单位
     */
    private FightHero lastAttack;

    private int addDamage;


    @Override
    public void beforeAction() {
        if (lastAttack != null && !lastAttack.isDead()) {
            addDamage += (int) (damage * 0.1);

        } else {
            lastAttack = fightScene.getEnemy(getSide());
            addDamage = 0;
        }

        finalAttackDamage += addDamage;
        super.beforeAction();
    }

    @Override
    public void action() {
        if (lastAttack == null) {
            // 战斗结束
            return;
        }
        // 攻击
        attack(lastAttack, new NormalAttackDamage(this, lastAttack, finalAttackDamage));

        // 溅射
        RangeAttackSkill skill1 = new RangeAttackSkill(0.25);
        skill1.setPos(lastAttack.getPos());
        skill1.action(this);
    }

}
