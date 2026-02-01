package org.elementarclash.game.event;

import lombok.Getter;

/**
 * Base class for all game events.
 */
@Getter
public abstract class GameEvent {

    private final long timestamp;

    protected GameEvent() {
        this.timestamp = System.currentTimeMillis();
    }

    public abstract EventType getEventType();

    public abstract String getDescription();
}