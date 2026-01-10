package org.elementarclash.units.air;

import org.elementarclash.faction.Faction;
import org.elementarclash.units.Unit;
import org.elementarclash.units.UnitStats;
import org.elementarclash.units.UnitType;
import org.elementarclash.units.strategy.movement.FlyingMovementStrategy;
import org.elementarclash.units.strategy.attack.MeleeAttackStrategy;

/**
 * Air faction's highly mobile melee striker.
 * <p>
 * Faction: Air | Movement: Flying | Attack: Melee (range: 1)
 * <p>
 * Special: Highest mobility (6 Movement). Flying ignores all terrain costs.
 * <p>
 * Tactical: Hit-and-run specialist. Uses superior mobility to flank and escape.
 * Can reach any position on the battlefield quickly.
 */
class WindDancer extends Unit {
    public WindDancer(String id, UnitStats stats) {
        super(id, "Wind-Tänzer", Faction.AIR, UnitType.WIND_DANCER, stats);
        setMovementStrategy(new FlyingMovementStrategy());
        setAttackStrategy(new MeleeAttackStrategy());
    }

    @Override
    public String getDescription() {
        return "Fliegend, höchste Mobilität (6 Bewegung)";
    }
}
