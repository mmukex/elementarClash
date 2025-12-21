package org.elementarclash.units.earth;

import org.elementarclash.faction.Faction;
import org.elementarclash.units.Unit;
import org.elementarclash.units.UnitStats;
import org.elementarclash.units.UnitType;
import org.elementarclash.units.strategy.attack.AreaAttackStrategy;
import org.elementarclash.units.strategy.movement.GroundMovementStrategy;
import org.elementarclash.units.strategy.attack.MeleeAttackStrategy;

/**
 * Earthquake Titan - Earth faction heavy damage dealer.
 * Special: Area-of-effect attack (damages all adjacent enemies).
 */
class EarthquakeTitan extends Unit {
    private static final int AOE_DAMAGE_MULTIPLIER = 75; // 75% of normal damage

    public EarthquakeTitan(String id, UnitStats stats) {
        super(id, "Erdbeben-Titan", Faction.EARTH, UnitType.EARTHQUAKE_TITAN, stats);
        setMovementStrategy(new GroundMovementStrategy(Faction.EARTH));
        setAttackStrategy(new AreaAttackStrategy(
                new MeleeAttackStrategy(),
                0.75
        ));
    }

    @Override
    public String getSpecialAbility() {
        return "Flächenangriff (trifft alle angrenzenden Gegner, 75% Schaden)";
    }

    /**
     * Returns true if this unit can use area-of-effect attacks.
     * Used by combat system (Chain of Responsibility pattern).
     */
    public boolean hasAreaOfEffectAttack() {
        return true;
    }

    /**
     * Returns the damage multiplier for AOE attacks (as percentage).
     */
    public int getAoeDamagePercent() {
        return AOE_DAMAGE_MULTIPLIER;
    }
}
