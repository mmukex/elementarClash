package org.elementarclash.game.combat;

import org.elementarclash.battlefield.visitor.TerrainEffectResult;

/**
 * Result of damage calculation with breakdown for debugging and UI display.
 *
 * @param totalDamage final damage after all modifiers (minimum 1)
 * @param baseDamage base damage from attacker's attack strategy
 * @param attackerEffect terrain effects on attacker
 * @param defenderEffect terrain effects on defender
 */
public record DamageResult(
    int totalDamage,
    int baseDamage,
    TerrainEffectResult attackerEffect,
    TerrainEffectResult defenderEffect
) {}
