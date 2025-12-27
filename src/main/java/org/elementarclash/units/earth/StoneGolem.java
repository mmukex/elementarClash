package org.elementarclash.units.earth;

import org.elementarclash.faction.Faction;
import org.elementarclash.units.Unit;
import org.elementarclash.units.UnitStats;
import org.elementarclash.units.UnitType;
import org.elementarclash.units.strategy.movement.GroundMovementStrategy;
import org.elementarclash.units.strategy.attack.MeleeAttackStrategy;

/**
 * Earth faction's immovable tank with terrain regeneration.
 * <p>
 * Faction: Earth | Movement: Ground | Attack: Melee (range: 1)
 * <p>
 * Special: Regenerates 5 HP per turn on stone terrain. Immovable (cannot be pushed).
 * <p>
 * Tactical: Defensive anchor. Guards key positions. Strongest on stone terrain.
 */
class StoneGolem extends Unit {
    private static final int MOUNTAIN_REGEN = 5;

    public StoneGolem(String id, UnitStats stats) {
        super(id, "Stein-Golem", Faction.EARTH, UnitType.STONE_GOLEM, stats);
        setMovementStrategy(new GroundMovementStrategy(Faction.EARTH));
        setAttackStrategy(new MeleeAttackStrategy());
    }

    @Override
    public String getSpecialAbility() {
        return "Regeneriert 5 LP pro Runde auf Berg-Gelände";
    }

    public int getMountainRegeneration() {
        return MOUNTAIN_REGEN;
    }
}
