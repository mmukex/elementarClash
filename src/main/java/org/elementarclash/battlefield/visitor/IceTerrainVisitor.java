package org.elementarclash.battlefield.visitor;

import org.elementarclash.battlefield.Terrain;
import org.elementarclash.units.Unit;

/**
 * Visitor for ICE terrain effects.
 * <p>
 * Effects:
 * - All units: +1 Defense (slippery ice provides cover)
 * - Water units: +3 Defense total (+1 base + +2 Water bonus), Heal 5 HP per turn
 * - Fire units: +1 Defense, Melts ice to Desert after movement
 * <p>
 * Design Pattern: Visitor (GoF #10) - Concrete Visitor
 *
 * @author mmukex
 */
public class IceTerrainVisitor extends AbstractTerrainVisitor {

    private static final int BASE_DEFENSE_BONUS = 1;
    private static final int WATER_DEFENSE_BONUS = 3;
    private static final int WATER_HEAL_PER_TURN = 5;

    public IceTerrainVisitor() {
        super(Terrain.ICE);
    }

    @Override
    public TerrainEffectResult visitFireUnit(Unit unit) {
        return new TerrainEffectResult(
                0,
                BASE_DEFENSE_BONUS,
                0,
                Terrain.DESERT,
                String.format("%s: +%d Verteidigung auf %s, schmilzt zu %s",
                        unit.getName(), BASE_DEFENSE_BONUS, getTerrainName(), Terrain.DESERT.getGermanName())
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

    @Override
    public TerrainEffectResult visitEarthUnit(Unit unit) {
        return createDefenseBonus(
                BASE_DEFENSE_BONUS,
                String.format("%s: +%d Verteidigung auf %s",
                        unit.getName(), BASE_DEFENSE_BONUS, getTerrainName())
        );
    }

    @Override
    public TerrainEffectResult visitAirUnit(Unit unit) {
        return createDefenseBonus(
                BASE_DEFENSE_BONUS,
                String.format("%s: +%d Verteidigung auf %s",
                        unit.getName(), BASE_DEFENSE_BONUS, getTerrainName())
        );
    }
}
