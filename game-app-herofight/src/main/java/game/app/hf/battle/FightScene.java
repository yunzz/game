package game.app.hf.battle;

import game.app.hf.battle.report.BattleReport;
import game.app.hf.battle.report.RoundReport;
import game.base.utils.ResourceUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 战斗场景
 *
 * @author Yunzhe.Jin
 * 2020/4/17 11:34
 */
public class FightScene {
    private FightPane first;
    private FightPane second;

    private CalcManager calcManager = new CalcManager();
    private List<FightHero> fightHeroes = new ArrayList<>();
    private BattleReport report = new BattleReport();
    private RoundReport currentRound;

    /**
     * 是否结束
     */
    private boolean end;

    public FightScene(FightPane first, FightPane second) {
        this.first = first;
        this.second = second;
        initAllHero();
        end = false;
    }

    public void start() {
        fightHeroes.forEach(FightHero::beforeStart);
        int round = 1;
        while (!end) {
            nextRound(round);
            round++;
        }
    }

    /**
     * 执行下一回合战斗
     *
     * @param round 第一回合：1
     */
    private void nextRound(int round) {

        RoundReport report = new RoundReport(round);
        currentRound = report;


        // 计算速度决定出手顺序
        calcSpeed();
        // 计算buff
        fightHeroes.forEach(FightHero::beforeRound);
        action();
        fightHeroes.forEach(FightHero::afterRound);

        end = checkWin();

        // 清理buff
        for (FightHero fightHero : fightHeroes) {
            fightHero.cleanBuff();
        }

        this.report.addRound(report);
    }

    /**
     * 检查胜利
     *
     * @return true : 结束，false: 没有结束
     */
    private boolean checkWin() {
        boolean f = first.hasAlive();
        boolean s = second.hasAlive();
        if (f && !s) {
            report.setWinSide(1);
            return true;
        } else if (!f && s) {
            report.setWinSide(2);
            return true;
        } else if (!f) {
            report.setWinSide(3);
            return true;
        }


        return false;
    }

    private void action() {
        for (FightHero fightHero : fightHeroes) {
            fightHero.beforeAction();
            if (fightHero.canAction) {
                fightHero.action();
            }
            fightHero.afterAction();
        }
    }

    /**
     * 随机获取一个敌人
     */
    public FightHero getEnemy(Side side) {
        if (side.flip() == Side.FIRST) {
            return first.getOne();
        }
        return second.getOne();
    }

    /**
     * 获取对方阵容
     */
    public FightPane oppositePane(Side side) {
        if (side == Side.FIRST) {
            return second;
        }
        return first;
    }

    private List<FightHero> initAllHero() {
        fightHeroes.addAll(first.all());
        fightHeroes.addAll(second.all());

        fightHeroes.forEach(fightHero -> {
            fightHero.setFightScene(this);
        });
        return fightHeroes;
    }

    private void calcSpeed() {
        fightHeroes.sort((o1, o2) -> Integer.compare(o2.getSpeed(), o1.getSpeed()));
    }


    public CalcManager getCalcManager() {
        return calcManager;
    }


    public RoundReport getCurrentRound() {
        return currentRound;
    }

    public BattleReport getReport() {
        return report;
    }
}
