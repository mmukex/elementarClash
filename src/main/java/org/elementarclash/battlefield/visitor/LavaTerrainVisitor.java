package org.elementarclash.battlefield.visitor;

import org.elementarclash.battlefield.Terrain;
import org.elementarclash.units.Unit;

/**
 * Visitor for LAVA terrain effects.
 * <p>
 * Effects:
 * - Fire units: +2 Attack
 * - Water units: -5 HP per turn
 * - Earth/Air: No effect (inherited from AbstractTerrainVisitor)
 * <p>
 * Design Pattern: Visitor (GoF #10) - Concrete Visitor
 */
public class LavaTerrainVisitor extends AbstractTerrainVisitor {

    private static final int FIRE_ATTACK_BONUS = 2;
    private static final int WATER_HP_DRAIN = -5;

    public LavaTerrainVisitor() {
        super(Terrain.LAVA);
    }

    @Override
    public TerrainEffectResult visitFireUnit(Unit unit) {
        return createAttackBonus(
            FIRE_ATTACK_BONUS,
            String.format("%s: +%d Angriff auf %s",
                unit.getName(), FIRE_ATTACK_BONUS, getTerrainName())
        );
    }

    @Override
    public TerrainEffectResult visitWaterUnit(Unit unit) {
        return createPerTurnEffect(
            WATER_HP_DRAIN,
            String.format("%s: %d LP pro Runde auf %s",
                unit.getName(), WATER_HP_DRAIN, getTerrainName())
        );
    }

    // visitEarthUnit() and visitAirUnit() return NEUTRAL (from base class)
}
