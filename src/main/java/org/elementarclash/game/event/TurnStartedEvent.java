package org.elementarclash.game.event;

import lombok.Getter;
import org.elementarclash.units.Faction;

@Getter
public class TurnStartedEvent extends GameEvent {

    private final Faction faction;

    public TurnStartedEvent(Faction faction) {
        super();
        this.faction = faction;
    }

    @Override
    public EventType getEventType() {
        return EventType.TURN_STARTED;
    }

    @Override
    public String getDescription() {
        return faction.name() + "'s turn started";
    }
}