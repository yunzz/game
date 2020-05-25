package game.base;

import java.util.LinkedList;

/**
 * @author yzz
 * 2020/3/17 3:41 下午
 */
public class LifeCycleBox {
    private LinkedList<LifeCycle> elementList = new LinkedList<>();


    public void add(LifeCycle lifeCycle) {
        elementList.add(lifeCycle);
    }

    public void start() {
        elementList.forEach(lifeCycle -> lifeCycle.start());
    }

    public void stop() {
        while (elementList.size() > 0) {
            try {
                elementList.removeLast().stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
