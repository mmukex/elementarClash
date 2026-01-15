package org.elementarclash.battlefield.terraineffect;

import org.elementarclash.battlefield.Terrain;
import org.elementarclash.units.Unit;

/**
 * Visitor for STONE terrain effects.
 * <p>
 * Effects:
 * - Earth units: +2 Defense
 * - Fire/Water/Air: No effect (inherited from AbstractTerrainVisitor)
 * <p>
 * Design Pattern: Visitor (GoF #10) - Concrete Visitor
 *
 * @author mmukex
 */
public class StoneTerrainVisitor extends AbstractTerrainVisitor {

    private static final int EARTH_DEFENSE_BONUS = 2;

    public StoneTerrainVisitor() {
        super(Terrain.STONE);
    }

    @Override
    public TerrainEffectResult visitEarthUnit(Unit unit) {
        return createDefenseBonus(
                EARTH_DEFENSE_BONUS,
                String.format("%s: +%d Verteidigung auf %s",
                        unit.getName(), EARTH_DEFENSE_BONUS, getTerrainName())
        );
    }

    // visitFireUnit(), visitWaterUnit(), visitAirUnit() return NEUTRAL (from base class)
}
