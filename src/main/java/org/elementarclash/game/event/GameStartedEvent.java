package org.elementarclash.game.event;

public class GameStartedEvent extends GameEvent {

    @Override
    public EventType getEventType() {
        return EventType.GAME_STARTED;
    }

    @Override
    public String getDescription() {
        return "Game started";
    }
}