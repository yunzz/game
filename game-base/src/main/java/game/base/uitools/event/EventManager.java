package game.base.uitools.event;

import game.base.uitools.event.events.ConsoleEvent;
import javafx.application.Platform;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * @author Yunzhe.Jin
 * 2020/4/7 15:38
 */
public class EventManager {

    private static Map<EventEnum, LinkedList<EventHandler>> events = new HashMap<>();

    public static <T extends Event> void addEventHandler(EventEnum eventEnum, EventHandler<T> eventHandler) {
        LinkedList<EventHandler> eventHandlers = events.computeIfAbsent(eventEnum, eventEnum1 -> {
            return new LinkedList<>();
        });
        eventHandlers.add(eventHandler);
    }

    public static void fireConsoleEvent(String event) {
        fireEvent(new ConsoleEvent(event));
    }

    public static void fireEvent(Event event) {
        Platform.runLater(() -> {
            LinkedList<EventHandler> eventHandler = events.get(event.eventEnum());
            if (eventHandler != null) {
                for (EventHandler handler : eventHandler) {
                    handler.onEvent(event);
                }
            }
        });
    }
}
