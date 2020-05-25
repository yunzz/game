package game.app.hf.battle.buff;

import game.app.hf.battle.FightHero;
import game.app.hf.battle.damage.Damage;

import java.util.Map;

/**
 * @author Yunzhe.Jin
 * 2020/4/18 10:18
 */
public abstract class BaseBuff implements IBuff {
    protected FightHero source;
    protected FightHero target;
    /**
     * 持续回数
     */
    protected int round;
    private final int id;
    protected Map<String,Object> config;

    public BaseBuff(BuffData buffData) {
        this.source = buffData.getSource();
        this.target = buffData.getTarget();
        this.id = buffData.getBuffId();
        this.round = buffData.getRound();
        this.config = buffData.getBuffConfig();
    }

    @Override
    public FightHero source() {
        return source;
    }

    @Override
    public FightHero target() {
        return target;
    }

    @Override
    public void beforeRound() {
    }

    @Override
    public void afterRound() {
        this.round--;
    }

    @Override
    public int order() {
        return 0;
    }

    @Override
    public void beforeAction() {

    }

    @Override
    public void afterAction() {

    }

    @Override
    public void beforeHurt(Damage calcDamage) {

    }

    @Override
    public int round() {
        return round;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    @Override
    public boolean expired() {
        return round <= 0;
    }

    @Override
    public int buffId() {
        return id;
    }

    @Override
    public Map<String,Object> report() {
        return null;
    }
}
