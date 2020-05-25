package game.app.hf.battle.buff;

import game.app.hf.battle.FightHero;

import java.util.Map;

/**
 * @author Yunzhe.Jin
 * 2020/4/18 11:22
 */
public class BuffData {

    protected FightHero source;
    protected FightHero target;
    private int round;
    private int buffId;
    private Map<String, Object> buffConfig;

    private BuffData() {
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        BuffData buffData = new BuffData();

        public BuffData build() {
            return buffData;
        }


        public Builder setSource(FightHero source) {
            buffData.source = source;
            return this;
        }

        public Builder setTarget(FightHero target) {
            buffData.target = target;
            return this;
        }

        public Builder setRound(int round) {
            buffData.round = round;
            return this;
        }

        public Builder setBuffConfig(Map<String, Object> config) {
            buffData.buffConfig = config;
            return this;
        }

        public Builder setBuffId(int buffId) {
            buffData.buffId = buffId;
            return this;
        }
    }


    public FightHero getSource() {
        return source;
    }

    public FightHero getTarget() {
        return target;
    }

    public int getRound() {
        return round;
    }

    public int getBuffId() {
        return buffId;
    }

    public Map<String, Object> getBuffConfig() {
        return buffConfig;
    }
}
