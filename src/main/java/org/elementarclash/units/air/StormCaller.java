package org.elementarclash.units.air;

import org.elementarclash.faction.Faction;
import org.elementarclash.units.Unit;
import org.elementarclash.units.UnitStats;
import org.elementarclash.units.UnitType;

/**
 * Storm Caller - Air faction ranged damage dealer.
 * Special: Chain lightning (attacks jump to nearby enemies, -2 damage per jump).
 */
public class StormCaller extends Unit {
    private static final int CHAIN_DAMAGE_REDUCTION = 2;
    private static final int MAX_CHAIN_TARGETS = 3;

    public StormCaller(String id, UnitStats stats) {
        super(id, "Sturm-Rufer", Faction.AIR, UnitType.STORM_CALLER, stats);
    }

    @Override
    public String getSpecialAbility() {
        return "Kettenblitz (springt auf nahe Gegner, -2 Schaden pro Sprung)";
    }

    /**
     * Returns true if this unit has chain lightning ability.
     * Used by combat system (Chain of Responsibility pattern).
     */
    public boolean hasChainLightning() {
        return true;
    }

    public int getChainDamageReduction() {
        return CHAIN_DAMAGE_REDUCTION;
    }

    public int getMaxChainTargets() {
        return MAX_CHAIN_TARGETS;
    }
}
