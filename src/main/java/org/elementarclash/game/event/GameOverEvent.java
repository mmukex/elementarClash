package org.elementarclash.game.event;

import lombok.Getter;
import org.elementarclash.faction.Faction;

@Getter
public class GameOverEvent extends GameEvent {

    private final Faction winner;

    public GameOverEvent(Faction winner) {
        super();
        this.winner = winner;
    }

    @Override
    public EventType getEventType() {
        return EventType.GAME_OVER;
    }

    @Override
    public String getDescription() {
        return "Game Over - " + winner.name() + " wins!";
    }

}