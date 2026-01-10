package org.elementarclash.units.fire;

import org.elementarclash.faction.Faction;
import org.elementarclash.units.Unit;
import org.elementarclash.units.UnitStats;
import org.elementarclash.units.UnitType;
import org.elementarclash.units.strategy.movement.GroundMovementStrategy;
import org.elementarclash.units.strategy.attack.RangedAttackStrategy;

/**
 * Fire faction's ranged unit that ignores forest defense bonuses.
 * <p>
 * Faction: Fire
 * <p>
 * Movement: Ground (Fire faction terrain modifiers: Lava cost 1.0, Ice cost 2.0)
 * Attack: Ranged (range: 3)
 * <p>
 * Special Ability: Ignores forest defense bonus.
 * Flame Archers' fire arrows burn through forest cover, negating defensive terrain advantages.
 * <p>
 * Tactical Use: Anti-forest ranged attacker.
 * Counters units hiding in forests. Good range but vulnerable to melee rushes.
 * Positioning critical for maximizing range advantage.
 */
class FlameArcher extends Unit {

    public FlameArcher(String id, UnitStats stats) {
        super(id, "Flammen-Bogenschütze", Faction.FIRE, UnitType.FLAME_ARCHER, stats);
        setMovementStrategy(new GroundMovementStrategy(Faction.FIRE));
        setAttackStrategy(new RangedAttackStrategy(true));
    }

    @Override
    public String getDescription() {
        return "Ignoriert Wald-Verteidigungsbonus (Reichweite: 3)";
    }
}