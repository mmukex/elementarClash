package units.impl.earth;

import faction.Faction;
import units.Unit;
import units.UnitStats;
import units.UnitType;

/**
 * Terra Shaman - Earth faction support unit.
 * Special: Can create earth walls (blocks movement for 2 turns).
 */
public class TerraShamane extends Unit {
    private static final int WALL_DURATION = 2;

    public TerraShamane(String id, UnitStats stats) {
        super(id, "Terra-Schamane", Faction.EARTH, UnitType.TERRA_SHAMAN, stats);
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
        return !hasActed();
    }

    public int getWallDuration() {
        return WALL_DURATION;
    }
}
