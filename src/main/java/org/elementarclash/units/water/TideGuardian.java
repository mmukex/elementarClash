package org.elementarclash.units.water;

import org.elementarclash.faction.Faction;
import org.elementarclash.units.Unit;
import org.elementarclash.units.UnitStats;
import org.elementarclash.units.UnitType;
import org.elementarclash.units.strategy.movement.GroundMovementStrategy;
import org.elementarclash.units.strategy.attack.MeleeAttackStrategy;

/**
 * Water faction's defensive tank with ice terrain bonus.
 * <p>
 * Faction: Water | Movement: Ground | Attack: Melee (range: 1)
 * <p>
 * Special: +3 defense on ice terrain.
 * <p>
 * Tactical: Frontline defender. Strongest on ice. Anchors defensive positions.
 */
class TideGuardian extends Unit {
    public TideGuardian(String id, UnitStats stats) {
        super(id, "Gezeiten-Wächter", Faction.WATER, UnitType.TIDE_GUARDIAN, stats);
        setMovementStrategy(new GroundMovementStrategy(Faction.WATER));
        setAttackStrategy(new MeleeAttackStrategy());
    }

    @Override
    public String getSpecialAbility() {
        return "+3 Verteidigung auf Eis-Gelände";
    }
}