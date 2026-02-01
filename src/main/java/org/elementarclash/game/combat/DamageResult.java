package org.elementarclash.game.combat;

import org.elementarclash.battlefield.terraineffect.TerrainEffectResult;

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
}