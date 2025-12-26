package org.elementarclash.battlefield.effects;

import org.elementarclash.battlefield.Cell;
import org.elementarclash.battlefield.Terrain;
import org.elementarclash.battlefield.TerrainEffect;

/**
 * Transforms forest terrain into lava due to fire spreading.
 * Represents dynamic battlefield event where forests burn down.
 * <p>
 * Design Pattern: Strategy (GoF #5) - Concrete Strategy
 */
public class ForestFireEffect implements TerrainEffect {

    @Override
    public Terrain apply(Cell cell) {
        return cell.getTerrain() == Terrain.FOREST ? Terrain.LAVA : cell.getTerrain();
    }
}
