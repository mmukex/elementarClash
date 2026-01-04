package org.elementarclash.game.event.observer;

import org.elementarclash.game.event.GameEvent;
import org.elementarclash.game.event.GameObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * Logs all game events for replay functionality.
 */
public class EventLogObserver implements GameObserver {

    private final List<GameEvent> eventLog = new ArrayList<>();

    @Override
    public void onEvent(GameEvent event) {
        eventLog.add(event);

        // Optional: write to file for persistent replay
        // logToFile(event);
    }

    @Override
    public String getObserverName() {
        return "EventLogObserver";
    }

    public List<GameEvent> getEventLog() {
        return new ArrayList<>(eventLog);
    }

    public void printLog() {
        System.out.println("\n=== Event Log (" + eventLog.size() + " events) ===");
        for (int i = 0; i < eventLog.size(); i++) {
            GameEvent event = eventLog.get(i);
            System.out.println((i + 1) + ". [" + event.getEventType() + "] " + event.getDescription());
        }
        System.out.println("===================================\n");
    }
}