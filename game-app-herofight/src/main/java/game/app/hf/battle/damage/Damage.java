package game.app.hf.battle.damage;

import game.app.hf.battle.FightHero;
import game.app.hf.battle.report.action.Action;

/**
 * 伤害
 *
 * @author Yunzhe.Jin
 * 2020/4/18 15:06
 */
public class Damage {

    /**
     * 第一次发起的用户
     */
    private FightHero source;
    private FightHero target;
    private DamageType damageType;
    private int value;
    private int id;

    public Damage(FightHero source, FightHero target) {
        this.source = source;
        this.target = target;
    }

    /**
     * 伤害来源名
     */
    private String name;

    public Action action() {
        return null;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public FightHero getSource() {
        return source;
    }

    public void setSource(FightHero source) {
        this.source = source;
    }

    public DamageType getDamageType() {
        return damageType;
    }

    public void setDamageType(DamageType damageType) {
        this.damageType = damageType;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public FightHero getTarget() {
        return target;
    }

    public void setTarget(FightHero target) {
        this.target = target;
    }

    @Override
    public String toString() {
        return "Damage{" +
                "source=" + source +
                ", target=" + target +
                ", damageType=" + damageType +
                ", value=" + value +
                ", id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
