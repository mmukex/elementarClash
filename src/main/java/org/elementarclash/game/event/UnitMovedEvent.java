package org.elementarclash.game.event;

import org.elementarclash.units.Unit;
import org.elementarclash.util.Position;

public class UnitMovedEvent extends GameEvent {

    private final Unit unit;
    private final Position from;
    private final Position to;

    public UnitMovedEvent(Unit unit, Position from, Position to) {
        super();
        this.unit = unit;
        this.from = from;
        this.to = to;
    }

    @Override
    public EventType getEventType() {
        return EventType.UNIT_MOVED;
    }

    @Override
    public String getDescription() {
        return unit.getName() + " moved from " + from + " to " + to;
    }

    public Unit getUnit() {
        return unit;
    }

    public Position getFrom() {
        return from;
    }

    public Position getTo() {
        return to;
    }
}