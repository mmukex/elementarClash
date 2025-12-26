package org.elementarclash.battlefield.effects;

import org.elementarclash.battlefield.Cell;
import org.elementarclash.battlefield.Terrain;
import org.elementarclash.battlefield.TerrainEffect;

/**
 * Transforms desert terrain into ice due to geysir eruption.
 * Represents dynamic battlefield event where water freezes on impact.
 * <p>
 * Design Pattern: Strategy (GoF #5) - Concrete Strategy
 */
public class GeysirEffect implements TerrainEffect {

    @Override
    public Terrain apply(Cell cell) {
        return cell.getTerrain() == Terrain.DESERT ? Terrain.ICE : cell.getTerrain();
    }
}
