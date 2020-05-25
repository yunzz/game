package game.app.hf.battle.report.action;

import game.app.hf.battle.FightHero;
import game.app.hf.battle.skill.Skill;

/**
 * @author Yunzhe.Jin
 * 2020/4/20 11:10
 */
public class SkillAction extends Action {

    private Skill skill;

    public SkillAction(FightHero source, FightHero target, Skill skill) {
        super(source, target);
        this.skill = skill;
    }

}
