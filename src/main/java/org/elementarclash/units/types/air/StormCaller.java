package org.elementarclash.units.types.air;

import org.elementarclash.units.Faction;
import org.elementarclash.units.Unit;
import org.elementarclash.units.UnitStats;
import org.elementarclash.units.UnitType;
import org.elementarclash.units.strategy.movement.FlyingMovementStrategy;
import org.elementarclash.units.strategy.attack.RangedAttackStrategy;

/**
 * Air faction's flying ranged attacker.
 * <p>
 * Faction: Air | Movement: Flying | Attack: Ranged (range: 4)
 * <p>
 * Special: Flying + Ranged combination. Can attack from safe positions.
 * <p>
 * Tactical: Air superiority unit. Flying movement + long range allows attacking
 * while staying out of melee range. Strong against ground units.
 */
class StormCaller extends Unit {
    public StormCaller(String id, UnitStats stats) {
        super(id, "Sturm-Rufer", Faction.AIR, UnitType.STORM_CALLER, stats);
        setMovementStrategy(new FlyingMovementStrategy());
        setAttackStrategy(new RangedAttackStrategy(false));
    }

    @Override
    public String getDescription() {
        return "Fliegend, Fernkampf (Reichweite: 4)";
    }
}
