package org.elementarclash.units.earth;

import org.elementarclash.faction.Faction;
import org.elementarclash.units.Unit;
import org.elementarclash.units.UnitStats;
import org.elementarclash.units.UnitType;
import org.elementarclash.units.strategy.attack.RangedAttackStrategy;
import org.elementarclash.units.strategy.movement.GroundMovementStrategy;

/**
 * Terra Shaman - Earth faction support unit.
 * Special: Can create earth walls (blocks movement for 2 turns).
 */
class TerraShamane extends Unit {
    private static final int WALL_DURATION = 2;

    public TerraShamane(String id, UnitStats stats) {
        super(id, "Terra-Schamane", Faction.EARTH, UnitType.TERRA_SHAMAN, stats);
        setMovementStrategy(new GroundMovementStrategy(Faction.EARTH));
        setAttackStrategy(new RangedAttackStrategy(false));
    }

    @Override
    public String getSpecialAbility() {
        return "Kann Erdwände erschaffen (blockiert 2 Runden)";
    }

    /**
     * Creates an earth wall at a position.
     * Will be implemented with Command pattern for abilities.
     */
    public boolean canCreateWall() {
        return canAct();
    }

    public int getWallDuration() {
        return WALL_DURATION;
    }
}
