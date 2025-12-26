package org.elementarclash.game.combat;

import org.elementarclash.battlefield.Terrain;
import org.elementarclash.battlefield.visitor.TerrainEffectResult;
import org.elementarclash.battlefield.visitor.TerrainVisitor;
import org.elementarclash.battlefield.visitor.TerrainVisitorFactory;
import org.elementarclash.game.Game;
import org.elementarclash.units.Unit;

/**
 * Calculates combat damage considering base attack, terrain effects, and defense.
 * Uses Strategy Pattern (attack calculation) and Visitor Pattern (terrain effects).
 */
public class DamageCalculator {

    /**
     * Calculates total damage from attacker to target including all modifiers.
     *
     * @param attacker attacking unit
     * @param target defending unit
     * @param game game state for terrain lookup
     * @return damage calculation result with breakdown
     */
    public DamageResult calculateDamage(Unit attacker, Unit target, Game game) {
        int baseDamage = attacker.getAttackStrategy().calculateBaseDamage(attacker, target);

        TerrainEffectResult attackerEffect = getTerrainEffect(attacker, game);
        TerrainEffectResult defenderEffect = getTerrainEffect(target, game);

        int attackBonus = attackerEffect.attackBonus();
        int defenseBonus = defenderEffect.defenseBonus();

        int totalAttack = baseDamage + attackBonus;
        int totalDefense = target.getBaseStats().defense() + defenseBonus;
        int totalDamage = Math.max(1, totalAttack - totalDefense);

        return new DamageResult(
            totalDamage,
            baseDamage,
            attackerEffect,
            defenderEffect
        );
    }

    private TerrainEffectResult getTerrainEffect(Unit unit, Game game) {
        Terrain terrain = game.getTerrainAt(unit.getPosition());
        TerrainVisitor visitor = TerrainVisitorFactory.getVisitor(terrain);
        return unit.accept(visitor);
    }
}
