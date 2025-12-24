package org.elementarclash.battlefield.visitor;

import org.elementarclash.battlefield.Terrain;

/**
 * Immutable value object representing terrain effects on a unit.
 * Encapsulates combat bonuses, per-turn effects, and terrain transformations.
 * <p>
 * Design Pattern: Value Object
 * Why: Immutable, self-contained effect data prevents side effects.
 * Used as return type from TerrainVisitor visit methods.
 * <p>
 * Effect Types:
 * - Combat bonuses (attack/defense modifiers for battle calculations)
 * - Per-turn effects (HP drain/healing applied each turn)
 * - Terrain transformations (e.g., Ice → Desert when Fire unit moves)
 */
public record TerrainEffectResult(
        int attackBonus,
        int defenseBonus,
        int hpPerTurn,           // Positive = heal, Negative = damage
        Terrain terrainChange,   // Null if no change (e.g., Ice → Desert after Fire movement)
        String effectDescription // Human-readable description for UI
) {

    public static final TerrainEffectResult NEUTRAL = new TerrainEffectResult(
            0, 0, 0, null, "Keine Terrain-Effekte"
    );

    public TerrainEffectResult {
        if (attackBonus < -10 || attackBonus > 10) {
            throw new IllegalArgumentException("Attack bonus out of range: " + attackBonus);
        }
        if (defenseBonus < -10 || defenseBonus > 10) {
            throw new IllegalArgumentException("Defense bonus out of range: " + defenseBonus);
        }
        if (hpPerTurn < -20 || hpPerTurn > 20) {
            throw new IllegalArgumentException("HP per turn out of range: " + hpPerTurn);
        }
    }

    public boolean hasPerTurnEffect() {
        return hpPerTurn != 0;
    }
}
