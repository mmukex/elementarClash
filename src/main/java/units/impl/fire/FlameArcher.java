package units.impl.fire;

import faction.Faction;
import units.Unit;
import units.UnitStats;
import units.UnitType;

/**
 * Flame Archer - Fire faction ranged unit.
 * Special: Ignores forest defense bonus (range 3).
 */
public class FlameArcher extends Unit {

    public FlameArcher(String id, UnitStats stats) {
        super(id, "Flammen-Bogenschütze", Faction.FIRE, UnitType.FLAME_ARCHER, stats);
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