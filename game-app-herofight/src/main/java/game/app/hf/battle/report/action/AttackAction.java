package game.app.hf.battle.report.action;

import game.app.hf.battle.FightHero;
import game.app.hf.battle.report.ActionType;
import game.app.hf.battle.report.EffectType;

/**
 * @author Yunzhe.Jin
 * 2020/4/18 18:00
 */
public class AttackAction extends Action {
    private int damage;

    public AttackAction(FightHero source, FightHero target, EffectType effectType) {
        super(source, target);
        this.source = source;
        this.target = target;
        this.actionType = ActionType.attack;
        this.effectType = effectType;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }


}
