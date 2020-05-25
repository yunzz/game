package game.app.hf.battle.damage;

import game.app.hf.battle.FightHero;
import game.app.hf.battle.report.action.Action;
import game.app.hf.battle.report.action.SkillAction;
import game.app.hf.battle.skill.Skill;

/**
 * @author Yunzhe.Jin
 * 2020/4/18 15:24
 */
public class SkillDamage extends Damage {
    protected Skill skill;

    public SkillDamage(FightHero source, FightHero target, int value, Skill skill) {
        super(source, target);

        this.setSource(source);
        this.setDamageType(DamageType.SKILL);
        this.setValue(value);
        this.setId(skill.getId());
        this.setName(skill.getName());
        this.skill = skill;
    }

    @Override
    public Action action() {
        return new SkillAction(getSource(), getTarget(), this.skill);
    }
}
