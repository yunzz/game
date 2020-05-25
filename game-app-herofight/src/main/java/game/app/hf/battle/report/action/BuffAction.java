package game.app.hf.battle.report.action;

import game.app.hf.battle.buff.IBuff;
import game.app.hf.battle.report.ActionType;

/**
 * @author Yunzhe.Jin
 * 2020/4/18 17:40
 */
public class BuffAction extends Action {
    private IBuff buff;

    public BuffAction(IBuff buff, ActionType actionType) {
        super(buff.source(), buff.target());
        this.buff = buff;
        this.actionType = actionType;
    }
}
