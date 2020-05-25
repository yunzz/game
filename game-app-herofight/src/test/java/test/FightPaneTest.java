package test;

import game.app.hf.battle.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yunzhe.Jin
 * 2020/4/30 15:57
 */
public class FightPaneTest {

    @Test
    public void test1() {
        HeroTemplate h1 = template(1, "关羽", new Pos(0, 0), Side.FIRST);
        HeroTemplate h2 = template(2, "张飞", new Pos(0, 1), Side.FIRST);
        HeroTemplate h3 = template(3, "赵云", new Pos(0, 0), Side.SECOND);

        List<FightHero> l1 = new ArrayList<>();


        l1.add(h1.createHero());
        l1.add(h2.createHero());
        FightPane f1 = new FightPane(l1);

        List<FightHero> l2 = new ArrayList<>();
        l2.add(h3.createHero());

        FightPane f2 = new FightPane(l2);
        FightScene fs = new FightScene(f1, f2);
        fs.start();
        System.out.println("end");
    }

    private HeroTemplate template(int heroId, String name, Pos pos, Side side) {

        BaseHeroAttr attr = new BaseHeroAttr();
        attr.setHeroId(heroId);
        attr.setName(name);
        attr.setDamage(100);
        attr.setHp(1000);
        attr.setDef(100);
        attr.setAvoid(20);
        attr.setCritical(20);
        attr.setCriticalDamage(150);
        attr.setSpeed(100);
        attr.setAngry(0);

        return new HeroTemplate(attr, pos, side, DefaultHeroCreator.instance);
    }
}
