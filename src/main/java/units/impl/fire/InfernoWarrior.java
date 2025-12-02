package units.impl.fire;

import faction.Faction;
import units.Unit;
import units.UnitStats;
import units.UnitType;

/**
 * Inferno Warrior - Fire faction melee unit.
 * Special: +2 Attack on Lava terrain (will be implemented with Visitor pattern).
 */
public class InfernoWarrior extends Unit {
    private static final int LAVA_ATTACK_BONUS = 2;

    public InfernoWarrior(String id, UnitStats stats) {
        super(id, "Inferno-Krieger", Faction.FIRE, UnitType.INFERNO_WARRIOR, stats);
    }

    @Override
    public String getSpecialAbility() {
        return "+2 Angriff auf Lava-Gelände";
    }

    // Placeholder - will be replaced by Visitor pattern later
    @Override
    public int getTerrainAttackBonus() {
        // TODO: Replace with Visitor pattern when terrain system is implemented
        return 0; // Will be handled by TerrainVisitor
    }
}