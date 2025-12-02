package units.impl.air;

import faction.Faction;
import units.Unit;
import units.UnitStats;
import units.UnitType;

/**
 * Wind Dancer - Air faction fast melee unit.
 * Special: Can attack twice per turn if it moves at least 3 tiles.
 */
public class WindDancer extends Unit {
    private static final int DOUBLE_ATTACK_MOVEMENT_THRESHOLD = 3;

    public WindDancer(String id, UnitStats stats) {
        super(id, "Wind-Tänzer", Faction.AIR, UnitType.WIND_DANCER, stats);
    }

    @Override
    public String getSpecialAbility() {
        return "Kann 2× angreifen nach Bewegung ≥3 Felder";
    }

    /**
     * Checks if unit qualifies for double attack based on movement.
     * Will be used by combat system.
     */
    public boolean canDoubleAttack(int tilesMovedThisTurn) {
        return tilesMovedThisTurn >= DOUBLE_ATTACK_MOVEMENT_THRESHOLD;
    }

    public int getDoubleAttackThreshold() {
        return DOUBLE_ATTACK_MOVEMENT_THRESHOLD;
    }
}
