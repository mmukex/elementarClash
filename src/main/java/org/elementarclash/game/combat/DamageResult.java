package org.elementarclash.game.combat;

import org.elementarclash.battlefield.visitor.TerrainEffectResult;

import java.util.List;

/**
 * Result of damage calculation with detailed breakdown.
 *
 * @author @crstmkt (extended for Chain of Responsibility)
 */
public record DamageResult(
        int totalDamage,
        int baseDamage,
        double factionMultiplier,
        int terrainAttackBonus,
        int terrainDefenseBonus,
        int synergyBonus,
        int totalDefense,
        List<String> calculationSteps
) {
    // Old constructor for compatibility
    @Deprecated
    public DamageResult(
            int totalDamage,
            int baseDamage,
            TerrainEffectResult attackerEffect,
            TerrainEffectResult defenderEffect
    ) {
        this(
                totalDamage,
                baseDamage,
                1.0,
                attackerEffect.attackBonus(),
                defenderEffect.defenseBonus(),
                0,
                defenderEffect.defenseBonus(),
                List.of()
        );
    }
}