package game.app.hf.battle;

import org.apache.commons.lang3.RandomUtils;

/**
 * 计算英雄数值
 *
 * @author Yunzhe.Jin
 * 2020/4/17 14:45
 */
public class CalcManager {

    /**
     * 防御计算基数
     */
    private int defBase = 200;

    /**
     * 闪避基数
     */
    private int avoidBase = 200;

    /**
     * 暴击概率基数
     */
    private int criticalBase = 200;

    /**
     * 暴击伤害基数
     */
    private int criticalDamageBase = 100;

    /**
     * 减伤
     *
     * @param def
     * @return
     */
    public double defDamage(int def) {
        return 1.0 * def / (def + defBase);
    }

    /**
     * 计算最终受到的伤害
     *
     * @param hurt
     * @return
     */
    public int calcHurt(int hurt, int def) {
        return (int) (hurt * (1.0 - defDamage(def)));
    }

    /**
     * 闪避概率
     *
     * @param v
     * @return
     */
    public double avoid(int v) {
        return 1.0 * v / (v + avoidBase);
    }

    public boolean isHappened(double v) {
        double r = RandomUtils.nextDouble(0, 1);

        return r <= v;
    }

    public boolean isCritical(int val) {

        double v = critical(val);

        double r = RandomUtils.nextDouble(0, 1);

        return r <= v;
    }

    /**
     * 暴击概率
     *
     * @param v
     * @return
     */
    public double critical(int v) {
        return 1.0 * v / (v + criticalBase);
    }

    /**
     * 暴击伤害倍数
     *
     * @param v
     * @return
     */
    public double criticalDamage(int v) {
        return 1.0 * v / criticalDamageBase;
    }
}
