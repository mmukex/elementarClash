package org.elementarclash.units.types.earth;

import org.elementarclash.units.Faction;
import org.elementarclash.units.Unit;
import org.elementarclash.units.UnitStats;
import org.elementarclash.units.UnitType;
import org.elementarclash.units.strategy.attack.RangedAttackStrategy;
import org.elementarclash.units.strategy.movement.GroundMovementStrategy;

/**
 * Earth faction's ranged support unit.
 * <p>
 * Faction: Earth | Movement: Ground | Attack: Ranged (range: 3)
 * <p>
 * Tactical: Ranged support specialist. Provides backline damage.
 */
class TerraShamane extends Unit {
    public TerraShamane(String id, UnitStats stats) {
        super(id, "Terra-Schamane", Faction.EARTH, UnitType.TERRA_SHAMAN, stats);
        setMovementStrategy(new GroundMovementStrategy(Faction.EARTH));
        setAttackStrategy(new RangedAttackStrategy(false));
    }

    @Override
    public String getDescription() {
        return "Unterstützungseinheit mit Fernkampf";
    }
}
