package game.app.hf.battle.skill;

import game.app.hf.battle.FightHero;
import game.app.hf.battle.FightPane;
import game.app.hf.battle.FightScene;
import game.app.hf.battle.Pos;
import game.app.hf.battle.damage.NormalAttackDamage;
import game.app.hf.battle.damage.SkillDamage;

import java.util.List;

/**
 * 溅射
 *
 * @author Yunzhe.Jin
 * 2020/4/18 15:37
 */
public class RangeAttackSkill extends Skill {

    /**
     * 溅射伤害比例
     */
    private double rate;
    private Pos pos;

    public RangeAttackSkill(double rate) {
        this.rate = rate;

        // todo
        id = 100;
        name = "溅射";
    }

    @Override
    public void action(FightHero fightHero) {

        FightScene fightScene = fightHero.getFightScene();
        FightPane pane = fightScene.oppositePane(fightHero.getSide());
        List<FightHero> near = pane.near(pos);
        if (!near.isEmpty()) {
            for (FightHero hero : near) {
                fightHero.attack(hero, new SkillDamage(fightHero, hero, (int) (fightHero.getFinalAttackDamage() * rate), this));
            }
        }
    }

    public void setPos(Pos pos) {
        this.pos = pos;
    }
}
