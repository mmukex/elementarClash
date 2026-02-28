package org.elementarclash.game.event;

/**
 * @author @crstmk
 */
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