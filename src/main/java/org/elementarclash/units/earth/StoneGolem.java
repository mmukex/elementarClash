package org.elementarclash.units.earth;

import org.elementarclash.faction.Faction;
import org.elementarclash.units.Unit;
import org.elementarclash.units.UnitStats;
import org.elementarclash.units.UnitType;
import org.elementarclash.units.strategy.movement.GroundMovementStrategy;
import org.elementarclash.units.strategy.attack.MeleeAttackStrategy;

/**
 * Stone Golem - Earth faction tank unit.
 * Special: Regenerates 5 HP per turn on mountain terrain.
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

    @Override
    public int getTerrainDefenseBonus() {
        // TODO: Replace with Visitor pattern when terrain system is implemented
        return 0; // Will be handled by TerrainVisitor
    }

    /**
     * Returns regeneration amount on mountain terrain.
     * Will be used by terrain/turn system later.
     */
    public int getMountainRegeneration() {
        return MOUNTAIN_REGEN;
    }
}
