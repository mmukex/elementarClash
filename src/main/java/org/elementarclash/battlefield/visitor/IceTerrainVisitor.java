package org.elementarclash.battlefield.visitor;

import org.elementarclash.battlefield.Terrain;
import org.elementarclash.units.Unit;

/**
 * Visitor for ICE terrain effects.
 * <p>
 * Effects:
 * - Water units: +2 Defense, Heal 5 HP per turn
 * - Fire units: Melts ice to Desert
 * - Earth/Air: No effect (inherited from AbstractTerrainVisitor)
 * <p>
 * Design Pattern: Visitor (GoF #10) - Concrete Visitor
 */
public class IceTerrainVisitor extends AbstractTerrainVisitor {

    private static final int WATER_DEFENSE_BONUS = 2;
    private static final int WATER_HEAL_PER_TURN = 5;

    public IceTerrainVisitor() {
        super(Terrain.ICE);
    }

    @Override
    public TerrainEffectResult visitFireUnit(Unit unit) {
        return createTerrainChange(
            Terrain.DESERT,
            String.format("%s schmilzt %s → %s",
                unit.getName(), getTerrainName(), Terrain.DESERT.getGermanName())
        );
    }

    @Override
    public TerrainEffectResult visitWaterUnit(Unit unit) {
        return createDefenseAndHpEffect(
            WATER_DEFENSE_BONUS,
            WATER_HEAL_PER_TURN,
            String.format("%s: +%d Verteidigung, +%d LP/Runde auf %s",
                unit.getName(), WATER_DEFENSE_BONUS, WATER_HEAL_PER_TURN, getTerrainName())
        );
    }

    // visitEarthUnit() and visitAirUnit() return NEUTRAL (from base class)
}
