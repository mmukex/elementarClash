package org.elementarclash.game.event;

import org.elementarclash.faction.Faction;

public class TurnEndedEvent extends GameEvent {

    private final Faction faction;

    public TurnEndedEvent(Faction faction) {
        super();
        this.faction = faction;
    }

    @Override
    public EventType getEventType() {
        return EventType.TURN_ENDED;
    }

    @Override
    public String getDescription() {
        return faction.name() + "'s turn ended";
    }

    public Faction getFaction() {
        return faction;
    }
}