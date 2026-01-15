package org.elementarclash.battlefield.terraineffect;

import org.elementarclash.battlefield.Terrain;

/**
 * Visitor for DESERT terrain effects.
 * <p>
 * Effects:
 * - Neutral terrain: No bonuses for any faction
 * - Represents baseline terrain with no special interactions
 * <p>
 * Design Pattern: Visitor (GoF #10) - Concrete Visitor
 * Why: Maintains pattern consistency even for neutral terrain.
 * Desert acts as the "default" terrain after ice melting.
 *
 * @author mmukex
 */
public class DesertTerrainVisitor extends AbstractTerrainVisitor {

    public DesertTerrainVisitor() {
        super(Terrain.DESERT);
    }

    // All visit methods return NEUTRAL (inherited from AbstractTerrainVisitor)
}
