package org.elementarclash.units.air;

import org.elementarclash.faction.Faction;
import org.elementarclash.units.Unit;
import org.elementarclash.units.UnitStats;
import org.elementarclash.units.UnitType;
import org.elementarclash.units.strategy.movement.FlyingMovementStrategy;
import org.elementarclash.units.strategy.attack.RangedAttackStrategy;

/**
 * Air faction's balanced flying defender.
 * <p>
 * Faction: Air | Movement: Flying | Attack: Ranged (range: 2)
 * <p>
 * Special: Flying + Ranged with moderate range. Balanced stats.
 * <p>
 * Tactical: Versatile air unit. Good HP (85) and defense (5) for a flying unit.
 * Can engage at range while maintaining defensive position.
 */
class SkyGuardian extends Unit {
    public SkyGuardian(String id, UnitStats stats) {
        super(id, "Himmels-Wächter", Faction.AIR, UnitType.SKY_GUARDIAN, stats);
        setMovementStrategy(new FlyingMovementStrategy());
        setAttackStrategy(new RangedAttackStrategy(false));
    }

    @Override
    public String getDescription() {
        return "Fliegend, ausgewogener Verteidiger";
    }

    public boolean isFlying() {
        return true;
    }
}
