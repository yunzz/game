package game.app.hf.battle.report.action;

import game.app.hf.battle.FightHero;
import game.app.hf.battle.report.ActionType;
import game.app.hf.battle.report.EffectType;

/**
 * @author Yunzhe.Jin
 * 2020/4/18 17:41
 */
public class Action {
    protected FightHero source;
    protected FightHero target;
    protected ActionType actionType;
    protected EffectType effectType = EffectType.none;
    private int damage;

    public Action(FightHero source, FightHero target) {
        this.source = source;
        this.target = target;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public void setEffectType(EffectType effectType) {
        this.effectType = effectType;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
}
