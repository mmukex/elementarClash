package org.elementarclash.units.fire;

import org.elementarclash.faction.Faction;
import org.elementarclash.units.Unit;
import org.elementarclash.units.UnitStats;
import org.elementarclash.units.UnitType;
import org.elementarclash.units.strategy.movement.GroundMovementStrategy;
import org.elementarclash.units.strategy.attack.RangedAttackStrategy;

/**
 * Flame Archer - Fire faction ranged unit.
 * Special: Ignores forest defense bonus (range 3).
 */
class FlameArcher extends Unit {

    public FlameArcher(String id, UnitStats stats) {
        super(id, "Flammen-Bogenschütze", Faction.FIRE, UnitType.FLAME_ARCHER, stats);
        setMovementStrategy(new GroundMovementStrategy(Faction.FIRE));
        setAttackStrategy(new RangedAttackStrategy(true));
    }

    @Override
    public String getSpecialAbility() {
        return "Ignoriert Wald-Verteidigungsbonus (Reichweite: 3)";
    }

    /**
     * Returns true if this unit ignores forest defense.
     * Used by combat system (Chain of Responsibility pattern).
     */
    public boolean ignoresForestDefense() {
        return true;
    }
}