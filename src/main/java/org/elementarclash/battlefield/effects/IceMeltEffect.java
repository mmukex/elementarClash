package org.elementarclash.battlefield.effects;

import org.elementarclash.battlefield.Cell;
import org.elementarclash.battlefield.Terrain;
import org.elementarclash.battlefield.TerrainEffect;

/**
 * Transforms ice terrain into desert due to melting.
 * Represents dynamic battlefield event where ice melts from heat or fire units.
 * <p>
 * Design Pattern: Strategy (GoF #5) - Concrete Strategy
 */
public class IceMeltEffect implements TerrainEffect {

    @Override
    public Terrain apply(Cell cell) {
        return cell.getTerrain() == Terrain.ICE ? Terrain.DESERT : cell.getTerrain();
    }
}
