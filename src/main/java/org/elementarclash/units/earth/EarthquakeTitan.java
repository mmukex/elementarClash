package org.elementarclash.units.earth;

import org.elementarclash.faction.Faction;
import org.elementarclash.units.Unit;
import org.elementarclash.units.UnitStats;
import org.elementarclash.units.UnitType;
import org.elementarclash.units.strategy.movement.GroundMovementStrategy;
import org.elementarclash.units.strategy.attack.MeleeAttackStrategy;

/**
 * Earth faction's massive tank unit with high survivability.
 * <p>
 * Faction: Earth | Movement: Ground | Attack: Melee (range: 1)
 * <p>
 * Tactical: Frontline tank. High health and defense. Absorbs damage for team.
 * Requires support to maximize value.
 */
class EarthquakeTitan extends Unit {
    public EarthquakeTitan(String id, UnitStats stats) {
        super(id, "Erdbeben-Titan", Faction.EARTH, UnitType.EARTHQUAKE_TITAN, stats);
        setMovementStrategy(new GroundMovementStrategy(Faction.EARTH));
        setAttackStrategy(new MeleeAttackStrategy());
    }

    @Override
    public String getDescription() {
        return "Mächtiger Nahkämpfer mit hoher Gesundheit";
    }
}
