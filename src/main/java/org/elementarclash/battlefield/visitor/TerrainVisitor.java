package org.elementarclash.battlefield.visitor;

import org.elementarclash.battlefield.Terrain;
import org.elementarclash.units.Unit;

/**
 * Visitor Pattern for terrain-unit interactions.
 * Implements double dispatch to apply terrain-specific effects to units.
 * <p>
 * Design Pattern: Visitor (GoF #10)
 * Why: Separates terrain effect logic from Unit hierarchy.
 * Enables 5 terrain types × 4 factions = 20 combinations without
 * polluting Unit classes with terrain-specific code.
 * <p>
 * Effect Types:
 * - Combat bonuses (attack/defense modifiers)
 * - Per-turn effects (HP drain/healing)
 * - Terrain transformations (Ice melting, Forest burning)
 * <p>
 * Implementations:
 * - LavaTerrainVisitor: Fire +2 ATK, Water -5 HP/turn
 * - IceTerrainVisitor: Water +3 DEF + 5 HP/turn, Fire melts ice
 * - ForestTerrainVisitor: All +2 DEF, blocks ranged LOS
 * - StoneTerrainVisitor: Earth +2 DEF
 * - DesertTerrainVisitor: Neutral (no effects)
 * <p>
 * Double Dispatch Flow:
 * 1. Client calls: unit.accept(terrainVisitor)
 * 2. Unit dispatches by faction: visitor.visitFireUnit(this)
 * 3. Visitor applies terrain-specific logic
 * 4. Returns TerrainEffectResult with bonuses/effects
 */
public interface TerrainVisitor {
    /**
     * Applies terrain effects to a Fire faction unit.
     *
     * @param unit the fire unit visiting this terrain
     * @return terrain effect result with bonuses/penalties
     */
    TerrainEffectResult visitFireUnit(Unit unit);

    /**
     * Applies terrain effects to a Water faction unit.
     *
     * @param unit the water unit visiting this terrain
     * @return terrain effect result with bonuses/penalties
     */
    TerrainEffectResult visitWaterUnit(Unit unit);

    /**
     * Applies terrain effects to an Earth faction unit.
     *
     * @param unit the earth unit visiting this terrain
     * @return terrain effect result with bonuses/penalties
     */
    TerrainEffectResult visitEarthUnit(Unit unit);

    /**
     * Applies terrain effects to an Air faction unit.
     *
     * @param unit the air unit visiting this terrain
     * @return terrain effect result with bonuses/penalties
     */
    TerrainEffectResult visitAirUnit(Unit unit);

    /**
     * Returns the terrain type this visitor represents.
     *
     * @return the terrain type
     */
    Terrain getTerrainType();
}
