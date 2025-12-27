package org.elementarclash.units.air;

import org.elementarclash.faction.Faction;
import org.elementarclash.units.Unit;
import org.elementarclash.units.UnitStats;
import org.elementarclash.units.UnitType;
import org.elementarclash.units.strategy.movement.FlyingMovementStrategy;
import org.elementarclash.units.strategy.attack.RangedAttackStrategy;

/**
 * Air faction's ranged unit with knockback ability.
 * <p>
 * Faction: Air | Movement: Flying | Attack: Ranged (range: 3)
 * <p>
 * Special: Push Ability (PushAbility) - Knocks targets back 1 tile.
 * <p>
 * Tactical: Crowd control. Repositions enemies. Counters melee rushes.
 */
class StormCaller extends Unit {
    private static final int CHAIN_DAMAGE_REDUCTION = 2;
    private static final int MAX_CHAIN_TARGETS = 3;

    public StormCaller(String id, UnitStats stats) {
        super(id, "Sturm-Rufer", Faction.AIR, UnitType.STORM_CALLER, stats);
        setMovementStrategy(new FlyingMovementStrategy());
        setAttackStrategy(new RangedAttackStrategy(false));
    }

    @Override
    public String getSpecialAbility() {
        return "Kettenblitz (springt auf nahe Gegner, -2 Schaden pro Sprung)";
    }

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
