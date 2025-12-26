package org.elementarclash.battlefield;

/**
 * Strategy interface for terrain transformation effects.
 * Enables dynamic terrain changes based on game events.
 * <p>
 * Design Pattern: Strategy (GoF #5)
 * Why: Encapsulates different terrain transformation algorithms.
 * New effects can be added without modifying existing code.
 * Example: ForestFireEffect (FOREST→LAVA), GeysirEffect (DESERT→ICE).
 * <p>
 * Works with: Composite Pattern - effects can be applied to Cell, Region, or entire Battlefield.
 */
public interface TerrainEffect {
    Terrain apply(Cell cell);
}
