package org.elementarclash.units.earth;

import org.elementarclash.faction.Faction;
import org.elementarclash.units.Unit;
import org.elementarclash.units.UnitStats;
import org.elementarclash.units.UnitType;
import org.elementarclash.units.strategy.attack.AreaAttackStrategy;
import org.elementarclash.units.strategy.movement.GroundMovementStrategy;
import org.elementarclash.units.strategy.attack.MeleeAttackStrategy;

/**
 * Earth faction's massive unit with area-of-effect attack.
 * <p>
 * Faction: Earth | Movement: Ground | Attack: Area (range: 1, hits all adjacent enemies at 75% damage)
 * <p>
 * Special: Earthquake (AreaDamageAbility) - Damages all adjacent enemies simultaneously.
 * <p>
 * Tactical: Anti-cluster unit. Punishes grouped enemies. High value target requiring protection.
 */
class EarthquakeTitan extends Unit {
    private static final int AOE_DAMAGE_MULTIPLIER = 75;

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

    public boolean hasAreaOfEffectAttack() {
        return true;
    }

    public int getAoeDamagePercent() {
        return AOE_DAMAGE_MULTIPLIER;
    }
}
