package org.elementarclash.game.event;

import org.elementarclash.units.Unit;

public class UnitDeathEvent extends GameEvent {

    private final Unit unit;

    public UnitDeathEvent(Unit unit) {
        super();
        this.unit = unit;
    }

    @Override
    public EventType getEventType() {
        return EventType.UNIT_DIED;
    }

    @Override
    public String getDescription() {
        return unit.getName() + " has died";
    }

    public Unit getUnit() {
        return unit;
    }
}