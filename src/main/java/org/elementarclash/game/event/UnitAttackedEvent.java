package org.elementarclash.game.event;

import org.elementarclash.game.combat.DamageResult;
import org.elementarclash.units.Unit;

public class UnitAttackedEvent extends GameEvent {

    private final Unit attacker;
    private final Unit target;
    private final DamageResult damageResult;

    public UnitAttackedEvent(Unit attacker, Unit target, DamageResult damageResult) {
        super();
        this.attacker = attacker;
        this.target = target;
        this.damageResult = damageResult;
    }

    @Override
    public EventType getEventType() {
        return EventType.UNIT_ATTACKED;
    }

    @Override
    public String getDescription() {
        return attacker.getName() + " attacked " + target.getName() +
                " for " + damageResult.totalDamage() + " damage";
    }

    public Unit getAttacker() {
        return attacker;
    }

    public Unit getTarget() {
        return target;
    }

    public DamageResult getDamageResult() {
        return damageResult;
    }
}