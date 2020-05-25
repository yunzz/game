package game.base.uitools.event.events;


import game.base.uitools.event.Event;
import game.base.uitools.event.EventEnum;

/**
 * @author Yunzhe.Jin
 * 2020/4/7 19:36
 */
public class ConsoleEvent implements Event {
    private String text;

    public ConsoleEvent(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public EventEnum eventEnum() {
        return EventEnum.CONSOLE;
    }
}
