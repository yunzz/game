package game.app.hf.battle;

import game.app.hf.battle.heroes.GuanYuFightHero;
import game.app.hf.battle.heroes.ZhangFeiFightHero;
import game.app.hf.battle.heroes.ZhaoYunFightHero;

/**
 * @author Yunzhe.Jin
 * 2020/4/30 16:44
 */
public class DefaultHeroCreator implements HeroCreator {

    public static DefaultHeroCreator instance = new DefaultHeroCreator();

    @Override
    public FightHero create(int heroId) {

        switch (heroId) {
            case 1:
                return new GuanYuFightHero();
            case 2:
                return new ZhangFeiFightHero();
            case 3:
                return new ZhaoYunFightHero();
        }
        return null;
    }

}
