package org.elementarclash.units.water;

import org.elementarclash.battlefield.Terrain;
import org.elementarclash.faction.Faction;
import org.elementarclash.units.Unit;
import org.elementarclash.units.UnitStats;
import org.elementarclash.units.UnitType;
import org.elementarclash.units.strategy.movement.GroundMovementStrategy;
import org.elementarclash.units.strategy.attack.MeleeAttackStrategy;
import org.elementarclash.units.strategy.movement.SpecialMovementStrategy;

/**
 * Water faction's mobile melee unit with ice terrain affinity.
 * <p>
 * Faction: Water | Movement: Special (ice cost 1.0) | Attack: Melee (range: 1)
 * <p>
 * Special: Fast movement on ice terrain (cost 1.0).
 * <p>
 * Tactical: Rapid flanker on ice. High mobility for hit-and-run tactics.
 */
class WaveRider extends Unit {
    public WaveRider(String id, UnitStats stats) {
        super(id, "Wellen-Reiter", Faction.WATER, UnitType.WAVE_RIDER, stats);
        setMovementStrategy(new SpecialMovementStrategy(
                new GroundMovementStrategy(Faction.WATER),
                new Terrain[]{Terrain.ICE},
                1.0
        ));
        setAttackStrategy(new MeleeAttackStrategy());
    }

    @Override
    public String getSpecialAbility() {
        return "Kann durch Wasser/Eis mit Kosten 1 ziehen";
    }
}