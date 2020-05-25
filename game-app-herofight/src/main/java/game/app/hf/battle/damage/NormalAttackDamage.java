package game.app.hf.battle.damage;

import game.app.hf.battle.BattleIds;
import game.app.hf.battle.FightHero;
import game.app.hf.battle.report.EffectType;
import game.app.hf.battle.report.action.Action;
import game.app.hf.battle.report.action.AttackAction;

/**
 * @author Yunzhe.Jin
 * 2020/4/18 15:24
 */
public class NormalAttackDamage extends Damage {

    public NormalAttackDamage(FightHero source, FightHero target, int value) {
        super(source, target);
        this.setSource(source);
        this.setDamageType(DamageType.ATTACK);
        this.setValue(value);
        this.setId(BattleIds.attack_id);
        this.setName(BattleIds.attack_name);
    }

    @Override
    public Action action() {
        return new AttackAction(getSource(), getTarget(), EffectType.none);
    }
}
