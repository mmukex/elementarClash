package org.elementarclash.battlefield.visitor;

import org.elementarclash.battlefield.Terrain;
import org.elementarclash.units.Unit;

/**
 * Visitor for FOREST terrain effects.
 * <p>
 * Effects:
 * - All units: +2 Defense
 * - Blocks ranged attack line of sight (handled by AttackStrategy)
 * - Fire attacks can ignite forest → Lava (handled by TerrainEffect)
 * <p>
 * Design Pattern: Visitor (GoF #10) - Concrete Visitor
 * Why: Forest provides uniform bonus to all factions, making visitor logic simple.
 * Line-of-sight blocking is handled separately by RangedAttackStrategy.
 *
 * @author mmukex
 */
public class ForestTerrainVisitor extends AbstractTerrainVisitor {

    private static final int DEFENSE_BONUS = 2;

    public ForestTerrainVisitor() {
        super(Terrain.FOREST);
    }

    @Override
    public TerrainEffectResult visitFireUnit(Unit unit) {
        return createForestBonus(unit);
    }

    @Override
    public TerrainEffectResult visitWaterUnit(Unit unit) {
        return createForestBonus(unit);
    }

    @Override
    public TerrainEffectResult visitEarthUnit(Unit unit) {
        return createForestBonus(unit);
    }

    @Override
    public TerrainEffectResult visitAirUnit(Unit unit) {
        return createForestBonus(unit);
    }

    private TerrainEffectResult createForestBonus(Unit unit) {
        return createDefenseBonus(
                DEFENSE_BONUS,
                String.format("%s: +%d Verteidigung auf %s",
                        unit.getName(), DEFENSE_BONUS, getTerrainName())
        );
    }
}
