package game.app.hf.battle.skill;

import game.app.hf.battle.FightHero;
import game.app.hf.battle.FightScene;
import game.app.hf.battle.damage.SkillDamage;

/**
 * 连击
 *
 * @author Yunzhe.Jin
 * 2020/4/20 11:27
 */
public class ContinueAttackSkill extends Skill {
    private double rate;
    private FightHero target;

    public ContinueAttackSkill(double rate, FightHero target) {
        this.rate = rate;
        this.target = target;

        // todo
        id = 101;
        name = "连击";
    }


    @Override
    public void action(FightHero fightHero) {
        FightScene fightScene = fightHero.getFightScene();
        boolean happened = fightScene.getCalcManager().isHappened(rate);
        if (happened && target.canBeTarget()) {
            fightHero.attack(target, new SkillDamage(fightHero, target, fightHero.getFinalAttackDamage(), this));
        }
    }
}
