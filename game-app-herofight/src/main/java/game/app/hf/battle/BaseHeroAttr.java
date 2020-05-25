package game.app.hf.battle;

/**
 * @author Yunzhe.Jin
 * 2020/4/17 16:54
 */
public class BaseHeroAttr {

    /**
     * 英雄名
     */
    protected String name;

    /**
     * 英雄id
     */
    protected int heroId;
    /**
     * 基础伤害
     */
    protected int damage;
    /**
     * 血量
     */
    protected int hp;

    /**
     * 护甲
     */
    protected int def;

    /**
     * 闪避值
     */
    protected int avoid;

    /**
     * 暴击值
     */
    protected int critical;

    /**
     * 暴击伤害
     */
    protected int criticalDamage;

    /**
     * 出手速度
     */
    protected int speed;

    /**
     * 怒气
     */
    protected int angry;

    public int getAngry() {
        return angry;
    }

    public void setAngry(int angry) {
        this.angry = angry;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHeroId() {
        return heroId;
    }

    public void setHeroId(int heroId) {
        this.heroId = heroId;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getDef() {
        return def;
    }

    public void setDef(int def) {
        this.def = def;
    }

    public int getAvoid() {
        return avoid;
    }

    public void setAvoid(int avoid) {
        this.avoid = avoid;
    }

    public int getCritical() {
        return critical;
    }

    public void setCritical(int critical) {
        this.critical = critical;
    }

    public int getCriticalDamage() {
        return criticalDamage;
    }

    public void setCriticalDamage(int criticalDamage) {
        this.criticalDamage = criticalDamage;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
