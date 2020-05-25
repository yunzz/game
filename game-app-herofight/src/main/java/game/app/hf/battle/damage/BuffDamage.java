package game.app.hf.battle.damage;

import game.app.hf.battle.FightHero;
import game.app.hf.battle.buff.IBuff;
import game.app.hf.battle.skill.Skill;

/**
 * @author Yunzhe.Jin
 * 2020/4/18 15:24
 */
public class BuffDamage extends Damage {

    public BuffDamage(FightHero source, FightHero target, int value, IBuff buff) {
        super(source, target);
        this.setSource(source);
        this.setDamageType(DamageType.BUFF);
        this.setValue(value);
        this.setId(buff.buffId());
        this.setName(buff.name());
    }
}
