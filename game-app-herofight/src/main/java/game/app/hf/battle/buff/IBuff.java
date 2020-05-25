package game.app.hf.battle.buff;

import game.app.hf.battle.FightHero;
import game.app.hf.battle.damage.Damage;

import java.util.Comparator;

/**
 * @author Yunzhe.Jin
 * 2020/4/17 14:18
 */
public interface IBuff extends Comparable<IBuff> {


    BuffType type();

    /**
     * buff的来源英雄
     *
     * @return
     */
    FightHero source();

    /**
     * 拥有buff的英雄
     *
     * @return
     */
    FightHero target();

    String name();

    void beforeRound();

    void afterRound();

    int order();

    @Override
    default int compareTo(IBuff o2) {
        return Integer.compare(this.order(), o2.order());
    }

    void beforeAction();

    void afterAction();

    /**
     * 受到伤害之前计算最终伤害
     *
     * @param calcDamage
     */
    void beforeHurt(Damage calcDamage);

    /**
     * 是否过期
     *
     * @return
     */
    default boolean expired() {
        return round() <= 0;
    }

    int buffId();

    int round();

    Object report();

}
