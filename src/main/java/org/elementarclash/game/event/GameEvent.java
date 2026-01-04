package org.elementarclash.game.event;

/**
 * Base class for all game events.
 */
public abstract class GameEvent {

    private final long timestamp;

    protected GameEvent() {
        this.timestamp = System.currentTimeMillis();
    }

    public long getTimestamp() {
        return timestamp;
    }

    public abstract EventType getEventType();

    public abstract String getDescription();
}