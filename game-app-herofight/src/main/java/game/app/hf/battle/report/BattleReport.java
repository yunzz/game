package game.app.hf.battle.report;

import game.app.hf.battle.report.action.Action;

import java.util.LinkedList;

/**
 * 战报
 *
 * @author Yunzhe.Jin
 * 2020/4/17 17:12
 */
public class BattleReport {

    private LinkedList<RoundReport> rounds = new LinkedList<>();

    /**
     * 回合外的动作，大部分是开始
     */
    private LinkedList<Action> actions = new LinkedList<>();

    /**
     * 1:first
     * 2:second
     * 3: 平局
     */
    private int winSide;

    public void addRound(RoundReport report) {
        rounds.add(report);
    }

    public int getWinSide() {
        return winSide;
    }

    public void setWinSide(int winSide) {
        this.winSide = winSide;
    }

    public void addAction(Action action) {
        actions.add(action);
    }
}
