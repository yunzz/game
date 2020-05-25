package game.app.hf.battle;

import game.app.hf.battle.buff.IBuff;
import game.app.hf.battle.damage.Damage;
import game.app.hf.battle.report.ActionType;
import game.app.hf.battle.report.BattleReport;
import game.app.hf.battle.report.EffectType;
import game.app.hf.battle.report.RoundReport;
import game.app.hf.battle.report.action.Action;
import game.app.hf.battle.report.action.AttackAction;
import game.app.hf.battle.report.action.BuffAction;
import game.base.log.Logs;

import java.util.Iterator;
import java.util.Objects;
import java.util.PriorityQueue;

/**
 * 战斗中英雄的状态
 *
 * @author Yunzhe.Jin
 * 2020/4/17 14:34
 */
public abstract class FightHero extends BaseHeroAttr {

    /**
     * 所在的战斗场景
     */
    protected FightScene fightScene;

    /**
     * 最后一次出手回合
     */
    protected int lastRound;

    /**
     * 所处位置
     */
    protected Pos pos;

    /**
     * 阵营
     */
    protected Side side;

    /**
     * 能否出手
     */
    protected boolean canAction = true;

    /**
     * buff
     */
    protected PriorityQueue<IBuff> buffs = new PriorityQueue<>();
    /**
     * 最终攻击伤害
     */
    protected int finalAttackDamage;

    /**
     * 最终护甲值
     */
    protected int finalDef;

    /**
     * 最终闪避值
     */
    protected int finalAvoid;
    /**
     * 最终暴击概率
     */
    protected int finalCritical;
    /**
     * 最终被暴击概率
     */
    protected int finalCritical0;
    /**
     * 最终暴击伤害
     */
    protected int finalCriticalDamage;
    /**
     * 最终被暴击伤害
     */
    protected int finalCriticalDamage0;
    /**
     * 是否可成为目标
     */
    private boolean canTarget = true;

    /**
     * 给目标增加buff
     *
     * @param buff
     * @return
     */
    public boolean addBuff(FightHero source, IBuff buff) {

        if (beforeAddBuff(buff)) {
            buffs.add(buff);
            Action action = new BuffAction(buff, ActionType.add_buff);
            if (getCurrentRound() != null) {
                getCurrentRound().addAction(action);
            } else {
                getBattleReport().addAction(action);
            }
            return true;
        }
        return false;
    }

    public void removeBuff(FightHero source, IBuff buff) {
        if (buffs.remove(buff)) {
            Action action = new BuffAction(buff, ActionType.remove_buff);
            getCurrentRound().addAction(action);
            afterRemoveBuff(buff);
        }
    }

    protected void afterRemoveBuff(IBuff buff) {

    }

    /**
     * @param buff
     * @return true:添加成功
     */
    protected boolean beforeAddBuff(IBuff buff) {
        return true;
    }

    public void cleanBuff() {
        Iterator<IBuff> iterator = buffs.iterator();
        if (iterator.hasNext()) {
            IBuff next = iterator.next();
            if (next.expired()) {
                iterator.remove();
            }
        }
    }

    /**
     * 战斗开始前操作
     */
    public void beforeStart() {

    }

    /**
     * 每个回合开始前，在所有英雄出手前
     *
     * @param round
     */
    public void beforeRound() {
        initData();
        for (IBuff buff : buffs) {
            buff.beforeRound();
        }
    }

    private void initData() {
        finalAttackDamage = damage;
        finalDef = def;
        finalAvoid = avoid;
        finalCritical = critical;
        finalCriticalDamage = criticalDamage;
    }


    /**
     * 每个回合结束后,在所有英雄出手后
     *
     * @param round
     */
    public void afterRound() {
        for (IBuff buff : buffs) {
            buff.afterRound();
        }
    }

    /**
     * 出手之前
     *
     * @param round
     */
    public void beforeAction() {
        for (IBuff buff : buffs) {
            buff.beforeAction();
        }
    }

    /**
     * 出手
     */
    public abstract void action();

    /**
     * 出手之后
     *
     * @param round
     */
    public void afterAction() {
        for (IBuff buff : buffs) {
            buff.afterAction();
        }
    }

    /**
     * 攻击
     *
     * @param target
     */
    public void attack(FightHero target, Damage damage) {
        target.attacked(this, damage);
    }

    /**
     * 被攻击
     *
     * @param source
     * @param damage
     */
    private void attacked(FightHero source, Damage damage) {
        RoundReport currentRound = fightScene.getCurrentRound();
        Action attackAction = damage.action();
        // 根据对方数值更新效果
        finalCritical0 = source.finalCritical;
        finalCriticalDamage0 = source.finalCriticalDamage;

        // 来自对方的最终伤害
        Damage calcDamage = damage;
        try {

            // 计算伤害之前处理
            beforeHurt(calcDamage);

            int totalDamage = calcDamage.getValue();

            // 伤害被转移
            if (totalDamage == 0) {
                return;
            }

            // 先计算buff
            for (IBuff buff : buffs) {
                buff.beforeHurt(calcDamage);
            }
            totalDamage = calcDamage.getValue();

            // 根据闪避
            CalcManager calcManager = fightScene.getCalcManager();
            double avoid = calcManager.avoid(finalAvoid);

            if (calcManager.isHappened(avoid)) {
                attackAction.setEffectType(EffectType.avoid);
                Logs.common.debug("发生闪避：" + heroId);
                return;
            }

            // 暴击
            boolean critical = calcManager.isCritical(finalCritical0);
            if (critical) {
                totalDamage *= calcManager.criticalDamage(finalCriticalDamage0);
                attackAction.setEffectType(EffectType.critical);
            }

            // 护甲
            totalDamage = calcManager.calcHurt(totalDamage, finalDef);
            calcDamage.setValue(totalDamage);

            attackAction.setDamage(calcDamage.getValue());
        } finally {
            currentRound.addAction(attackAction);
        }
        // 减血
        reduceHp(calcDamage);
    }

    protected void beforeHurt(Damage damage) {

    }

    private void reduceHp(Damage damage) {
        hp -= damage.getValue();
    }

    /**
     * 是否死亡
     *
     * @return
     */
    public boolean isDead() {
        return hp <= 0;
    }

    /**
     * 是否能成为目标
     *
     * @return
     */
    public boolean canBeTarget() {
        return !isDead() && canTarget;
    }

    /**
     * 减少攻击伤害
     */
    public void decFinalDamage(double rate) {
        finalAttackDamage *= (1 - rate);
    }

    /**
     * 增加攻击伤害
     */
    public void incFinalDamage(double rate) {
        finalAttackDamage *= (1 + rate);
    }

    /**
     * 减少护甲
     */
    public void decFinalDef(double rate) {
        finalDef *= (1 - rate);
    }

    public RoundReport getCurrentRound() {
        return fightScene.getCurrentRound();
    }

    public BattleReport getBattleReport() {
        return fightScene.getReport();
    }

    public void addFinalAvoid(double avoidRate) {
        finalAvoid += finalAvoid * avoidRate;
    }

    /////////////////////////////
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

    public int getLastRound() {
        return lastRound;
    }

    public void setLastRound(int lastRound) {
        this.lastRound = lastRound;
    }

    public Pos getPos() {
        return pos;
    }

    public void setPos(Pos pos) {
        this.pos = pos;
    }

    public FightScene getFightScene() {
        return fightScene;
    }

    public void setFightScene(FightScene fightScene) {
        this.fightScene = fightScene;
    }

    public PriorityQueue<IBuff> getBuffs() {
        return buffs;
    }

    public Side getSide() {
        return side;
    }

    public void setSide(Side side) {
        this.side = side;
    }

    public int getHeroId() {
        return heroId;
    }

    public void setHeroId(int heroId) {
        this.heroId = heroId;
    }

    public boolean isCanAction() {
        return canAction;
    }

    public void setCanAction(boolean canAction) {
        this.canAction = canAction;
    }

    public int getFinalAttackDamage() {
        return finalAttackDamage;
    }

    public boolean isCanTarget() {
        return canTarget;
    }

    public void setCanTarget(boolean canTarget) {
        this.canTarget = canTarget;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FightHero fightHero = (FightHero) o;
        return heroId == fightHero.heroId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(heroId);
    }

    @Override
    public String toString() {
        return "FightHero{" +
                "pos=" + pos +
                ", side=" + side +
                ", name='" + name + '\'' +
                ", heroId=" + heroId +
                '}';
    }

}
