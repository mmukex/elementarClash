package org.elementarclash.units.water;

import org.elementarclash.faction.Faction;
import org.elementarclash.units.Unit;
import org.elementarclash.units.UnitStats;
import org.elementarclash.units.UnitType;
import org.elementarclash.units.strategy.movement.GroundMovementStrategy;
import org.elementarclash.units.strategy.attack.RangedAttackStrategy;

/**
 * Water faction's ranged spellcaster with long range.
 * <p>
 * Faction: Water
 * <p>
 * Movement: Ground (Water faction terrain modifiers: Ice cost 1.0, Lava cost 3.0)
 * Attack: Ranged (range: 4)
 * <p>
 * Tactical Use: Long-range damage dealer.
 * High range allows safe positioning. Strong against melee units. Vulnerable when cornered.
 * Requires careful positioning to maintain range advantage.
 */
class FrostMage extends Unit {
    public FrostMage(String id, UnitStats stats) {
        super(id, "Frost-Magier", Faction.WATER, UnitType.FROST_MAGE, stats);
        setMovementStrategy(new GroundMovementStrategy(Faction.WATER));
        setAttackStrategy(new RangedAttackStrategy(false));
    }

    @Override
    public String getDescription() {
        return "Fernkampf-Magier mit hoher Reichweite (4)";
    }
}