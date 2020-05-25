package game.app.hf.battle.report;

import game.app.hf.battle.report.action.Action;

import java.util.LinkedList;

/**
 * 一回合的数据
 *
 * @author Yunzhe.Jin
 * 2020/4/18 16:20
 */
public class RoundReport {
    private int round;
    private LinkedList<Action> actions = new LinkedList<>();

    public RoundReport(int round) {
        this.round = round;

    }

    public void addAction(Action action) {
        actions.add(action);
    }
}
