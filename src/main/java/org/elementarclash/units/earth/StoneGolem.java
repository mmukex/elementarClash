package org.elementarclash.units.earth;

import org.elementarclash.faction.Faction;
import org.elementarclash.units.Unit;
import org.elementarclash.units.UnitStats;
import org.elementarclash.units.UnitType;
import org.elementarclash.units.strategy.movement.GroundMovementStrategy;
import org.elementarclash.units.strategy.attack.MeleeAttackStrategy;

/**
 * Earth faction's massive defensive tank.
 * <p>
 * Faction: Earth | Movement: Ground | Attack: Melee (range: 1)
 * <p>
 * Special: Highest HP (140) and Defense (10) in the game. +5 Defense on Stone terrain.
 * Stone defense bonus implemented via Visitor Pattern: StoneTerrainVisitor.
 * <p>
 * Tactical: Defensive anchor. Guards key positions. Extremely hard to kill.
 */
class StoneGolem extends Unit {
    public StoneGolem(String id, UnitStats stats) {
        super(id, "Stein-Golem", Faction.EARTH, UnitType.STONE_GOLEM, stats);
        setMovementStrategy(new GroundMovementStrategy(Faction.EARTH));
        setAttackStrategy(new MeleeAttackStrategy());
    }

    @Override
    public String getDescription() {
        return "Massiver Tank (höchste LP & Verteidigung)";
    }
}
