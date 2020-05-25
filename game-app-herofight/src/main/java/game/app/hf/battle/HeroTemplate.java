package game.app.hf.battle;

import org.apache.commons.lang3.ClassUtils;

/**
 * @author Yunzhe.Jin
 * 2020/4/20 11:36
 */
public class HeroTemplate {

    private BaseHeroAttr attr;

    protected Pos pos;
    protected Side side;
    private HeroCreator heroCreator;

    public HeroTemplate(BaseHeroAttr attr, Pos pos, Side side, HeroCreator heroCreator) {
        this.attr = attr;
        this.pos = pos;
        this.side = side;
        this.heroCreator = heroCreator;
    }


    public FightHero createHero() {
        FightHero fightHero = heroCreator.create(attr.heroId);
        fightHero.setPos(pos);
        fightHero.setSide(side);
        fightHero.setName(attr.getName());
        fightHero.setHeroId(attr.getHeroId());
        fightHero.setDamage(attr.getDamage());
        fightHero.setHp(attr.getHp());
        fightHero.setDef(attr.getDef());
        fightHero.setAvoid(attr.getAvoid());
        fightHero.setCritical(attr.getCritical());
        fightHero.setCriticalDamage(attr.getCriticalDamage());
        fightHero.setSpeed(attr.getSpeed());
        fightHero.setAngry(attr.getAngry());

        return fightHero;
    }

    public BaseHeroAttr getAttr() {
        return attr;
    }


}
