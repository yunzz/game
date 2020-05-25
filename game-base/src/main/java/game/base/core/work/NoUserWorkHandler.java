package game.base.core.work;

/**
 * @author Yunzhe.Jin
 * 2020/4/4 21:04
 */
public class NoUserWorkHandler extends WorkHandler {


    @Override
    public void doEvent(WorkEvent event) throws Exception {
        doProcess(event.getMessage(), event.getChannel(), null);
    }
}
