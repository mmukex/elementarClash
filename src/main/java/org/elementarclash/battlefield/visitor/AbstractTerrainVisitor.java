package org.elementarclash.battlefield.visitor;

import org.elementarclash.battlefield.Terrain;
import org.elementarclash.units.Unit;

/**
 * Abstract base class for terrain visitors.
 * Provides default implementations and helper methods to reduce code duplication.
 * <p>
 * Design Pattern: Template Method + Visitor
 * Why: Eliminates boilerplate code in concrete visitors.
 * Follows DRY principle by centralizing common functionality.
 * <p>
 * Default Behavior:
 * - All visit methods return NEUTRAL by default
 * - Concrete visitors only override methods for non-neutral effects
 */
public abstract class AbstractTerrainVisitor implements TerrainVisitor {

    private final Terrain terrainType;

    protected AbstractTerrainVisitor(Terrain terrainType) {
        this.terrainType = terrainType;
    }

    @Override
    public Terrain getTerrainType() {
        return terrainType;
    }

    // Default implementations - return NEUTRAL for all factions
    @Override
    public TerrainEffectResult visitFireUnit(Unit unit) {
        return TerrainEffectResult.NEUTRAL;
    }

    @Override
    public TerrainEffectResult visitWaterUnit(Unit unit) {
        return TerrainEffectResult.NEUTRAL;
    }

    @Override
    public TerrainEffectResult visitEarthUnit(Unit unit) {
        return TerrainEffectResult.NEUTRAL;
    }

    @Override
    public TerrainEffectResult visitAirUnit(Unit unit) {
        return TerrainEffectResult.NEUTRAL;
    }

    protected TerrainEffectResult createAttackBonus(int bonus, String description) {
        return new TerrainEffectResult(bonus, 0, 0, null, description);
    }

    protected TerrainEffectResult createDefenseBonus(int bonus, String description) {
        return new TerrainEffectResult(0, bonus, 0, null, description);
    }

    protected TerrainEffectResult createDefenseAndHpEffect(int defenseBonus, int hpPerTurn, String description) {
        return new TerrainEffectResult(0, defenseBonus, hpPerTurn, null, description);
    }

    protected TerrainEffectResult createPerTurnEffect(int hpPerTurn, String description) {
        return new TerrainEffectResult(0, 0, hpPerTurn, null, description);
    }

    protected TerrainEffectResult createTerrainChange(Terrain newTerrain, String description) {
        return new TerrainEffectResult(0, 0, 0, newTerrain, description);
    }

    protected String getTerrainName() {
        return terrainType.getGermanName();
    }
}
