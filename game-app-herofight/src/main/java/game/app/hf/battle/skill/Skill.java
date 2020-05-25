package game.app.hf.battle.skill;

import game.app.hf.battle.FightHero;

/**
 * @author Yunzhe.Jin
 * 2020/4/17 14:20
 */
public class Skill {
    protected int id;
    protected String name;
    protected int cd;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void action(FightHero fightHero) {

    }
}
