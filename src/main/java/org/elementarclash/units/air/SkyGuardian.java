package org.elementarclash.units.air;

import org.elementarclash.faction.Faction;
import org.elementarclash.units.Unit;
import org.elementarclash.units.UnitStats;
import org.elementarclash.units.UnitType;
import org.elementarclash.units.strategy.movement.FlyingMovementStrategy;
import org.elementarclash.units.strategy.attack.RangedAttackStrategy;

/**
 * Sky Guardian - Air faction flying defender.
 * Special: Flying (ignores terrain), +2 defense when on high ground.
 */
class SkyGuardian extends Unit {
    private static final int HIGH_GROUND_DEFENSE_BONUS = 2;

    public SkyGuardian(String id, UnitStats stats) {
        super(id, "Himmels-Wächter", Faction.AIR, UnitType.SKY_GUARDIAN, stats);
        setMovementStrategy(new FlyingMovementStrategy());
        setAttackStrategy(new RangedAttackStrategy(false));
    }

    @Override
    public String getSpecialAbility() {
        return "Fliegend, +2 Verteidigung auf erhöhtem Gelände";
    }

    /**
     * Flying units ignore terrain movement penalties.
     */
    public boolean isFlying() {
        return true;
    }

    @Override
    public int getTerrainDefenseBonus() {
        // TODO: Replace with Visitor pattern when terrain system is implemented
        return 0; // Will be handled by TerrainVisitor
    }

    public int getHighGroundDefenseBonus() {
        return HIGH_GROUND_DEFENSE_BONUS;
    }
}
