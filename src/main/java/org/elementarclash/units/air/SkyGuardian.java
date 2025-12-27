package org.elementarclash.units.air;

import org.elementarclash.faction.Faction;
import org.elementarclash.units.Unit;
import org.elementarclash.units.UnitStats;
import org.elementarclash.units.UnitType;
import org.elementarclash.units.strategy.movement.FlyingMovementStrategy;
import org.elementarclash.units.strategy.attack.RangedAttackStrategy;

/**
 * Air faction's flying defender with vision bonus.
 * <p>
 * Faction: Air | Movement: Flying | Attack: Ranged (range: 3)
 * <p>
 * Special: Provides vision for allies. Flying movement ignores terrain.
 * <p>
 * Tactical: Scout and support. Reveals fog of war. Mobile defensive anchor.
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

    public boolean isFlying() {
        return true;
    }

    public int getHighGroundDefenseBonus() {
        return HIGH_GROUND_DEFENSE_BONUS;
    }
}
