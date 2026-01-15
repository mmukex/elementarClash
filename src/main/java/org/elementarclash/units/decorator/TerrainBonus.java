package org.elementarclash.units.decorator;

import org.elementarclash.battlefield.visitor.TerrainEffectResult;
import org.elementarclash.units.Unit;

/**
 * Terrain-based bonus from @mmukex Visitor Pattern.
 *
 * Integration Point: Converts TerrainEffectResult to Decorator
 *
 * // ToDo: Müll
 */
public class TerrainBonus extends UnitDecorator {

    private final TerrainEffectResult terrainEffect;

    public TerrainBonus(TerrainEffectResult effect) {
        this.terrainEffect = effect;
    }

    @Override
    public int getAttackBonus(Unit unit) {
        return terrainEffect.attackBonus();
    }

    @Override
    public int getDefenseBonus(Unit unit) {
        return terrainEffect.defenseBonus();
    }

    @Override
    public boolean isExpired() {
        return false; // Terrain bonuses are permanent (until unit moves)
    }

    @Override
    public void tick() {
        // No tick logic (permanent bonus)
    }

    @Override
    public String getDecoratorName() {
        return "TerrainBonus";
    }

    @Override
    public String getDescription() {
        return terrainEffect.effectDescription();
    }

    public TerrainEffectResult getTerrainEffect() {
        return terrainEffect;
    }
}