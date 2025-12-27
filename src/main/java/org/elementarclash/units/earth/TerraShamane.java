package org.elementarclash.units.earth;

import org.elementarclash.faction.Faction;
import org.elementarclash.units.Unit;
import org.elementarclash.units.UnitStats;
import org.elementarclash.units.UnitType;
import org.elementarclash.units.strategy.attack.RangedAttackStrategy;
import org.elementarclash.units.strategy.movement.GroundMovementStrategy;

/**
 * Earth faction's support unit that creates defensive terrain.
 * <p>
 * Faction: Earth | Movement: Ground | Attack: Ranged (range: 3)
 * <p>
 * Special: Create Wall (CreateWallAbility) - Creates stone terrain walls.
 * <p>
 * Tactical: Battlefield control specialist. Creates choke points and defensive positions.
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

    public boolean canCreateWall() {
        return canAct();
    }

    public int getWallDuration() {
        return WALL_DURATION;
    }
}
