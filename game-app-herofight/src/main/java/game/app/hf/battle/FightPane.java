package game.app.hf.battle;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 英雄站位 3 * 3 的格子
 * <p>
 * -----------------
 * (0,0),(0,1),(0,2)
 * (1,0),(1,1),(1,2)
 * (2,0),(2,1),(2,2)
 * -----------------
 *
 * @author Yunzhe.Jin
 * 2020/4/17 11:35
 */
public class FightPane {
    private FightHero[][] grid = new FightHero[3][3];
    private List<FightHero> fightHeroList;

    public FightPane(List<FightHero> fightHeroList) {
        this.fightHeroList = fightHeroList;

        for (FightHero fightHero : fightHeroList) {
            grid[fightHero.getPos().x][fightHero.getPos().y] = fightHero;
        }
    }

    public FightHero getAt(Pos pos) {
        return grid[pos.x][pos.y];
    }

    public List<FightHero> all() {
        return fightHeroList;
    }

    public FightHero getOne() {
        for (FightHero fightHero : fightHeroList) {
            if (fightHero.canBeTarget()) {
                return fightHero;
            }
        }

        return null;
    }

    public boolean hasAlive() {
        for (FightHero fightHero : fightHeroList) {
            if (!fightHero.isDead()) {
                return true;
            }
        }

        return false;
    }


    public List<FightHero> getAllExcept(FightHero hero) {
        return fightHeroList.stream().filter(fightHero ->
                !fightHero.isDead() && hero.heroId != fightHero.heroId).collect(Collectors.toList());

    }

    /**
     * 返回位置附近的敌人
     *
     * @param pos
     * @return
     */
    public List<FightHero> near(Pos pos) {
        List<Pos> near = pos.near();
        return fightHeroList.stream().filter(fightHero ->
                !fightHero.isDead() && near.contains(fightHero.pos)).collect(Collectors.toList());
    }

}
