package org.elementarclash.units.water;

import org.elementarclash.faction.Faction;
import org.elementarclash.units.Unit;
import org.elementarclash.units.UnitStats;
import org.elementarclash.units.UnitType;
import org.elementarclash.units.strategy.movement.GroundMovementStrategy;
import org.elementarclash.units.strategy.attack.RangedAttackStrategy;

/**
 * Water faction's ranged spellcaster with slowing attacks.
 * <p>
 * Faction: Water
 * <p>
 * Movement: Ground (Water faction terrain modifiers: Ice cost 1.0, Lava cost 3.0)
 * Attack: Ranged (range: 3)
 * <p>
 * Special Ability: Slow Ability (SlowAbility).
 * Frost Mage attacks reduce target movement by 1 for 2 turns, limiting enemy mobility.
 * <p>
 * Tactical Use: Crowd control and kiting.
 * Slows enemies to create distance. Strong against melee units. Vulnerable when cornered.
 * Requires careful positioning to maintain range advantage.
 */
class FrostMage extends Unit {
    public FrostMage(String id, UnitStats stats) {
        super(id, "Frost-Magier", Faction.WATER, UnitType.FROST_MAGE, stats);
        setMovementStrategy(new GroundMovementStrategy(Faction.WATER));
        setAttackStrategy(new RangedAttackStrategy(false));
    }

    @Override
    public String getSpecialAbility() {
        return "Angriffe verlangsamen Gegner (-1 Bewegung für 2 Runden)";
    }

    public boolean appliesSlowEffect() {
        return true;
    }
}