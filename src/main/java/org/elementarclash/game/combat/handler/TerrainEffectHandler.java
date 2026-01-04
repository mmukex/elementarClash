package org.elementarclash.game.combat.handler;

import org.elementarclash.battlefield.Terrain;
import org.elementarclash.battlefield.visitor.TerrainEffectResult;
import org.elementarclash.battlefield.visitor.TerrainVisitor;
import org.elementarclash.battlefield.visitor.TerrainVisitorFactory;
import org.elementarclash.game.combat.DamageContext;

/**
 * Apply terrain effects using @mmukex Visitor Pattern.
 *
 * Integration Point: TerrainVisitorFactory from @mmukex Pattern #10
 */
public class TerrainEffectHandler extends DamageHandler {

    @Override
    public void handle(DamageContext context) {
        // INTEGRATION WITH @mmukex VISITOR PATTERN!

        // Attacker terrain effects
        Terrain attackerTerrain = context.getGame().getTerrainAt(context.getAttacker().getPosition());
        TerrainVisitor attackerVisitor = TerrainVisitorFactory.getVisitor(attackerTerrain);
        TerrainEffectResult attackerEffect = context.getAttacker().accept(attackerVisitor);

        if (attackerEffect.attackBonus() != 0) {
            context.addTerrainAttackBonus(attackerEffect.attackBonus());
        }

        // Defender terrain effects
        Terrain defenderTerrain = context.getGame().getTerrainAt(context.getTarget().getPosition());
        TerrainVisitor defenderVisitor = TerrainVisitorFactory.getVisitor(defenderTerrain);
        TerrainEffectResult defenderEffect = context.getTarget().accept(defenderVisitor);

        if (defenderEffect.defenseBonus() != 0) {
            context.addTerrainDefenseBonus(defenderEffect.defenseBonus());
        }

        // Pass to next handler
        super.handle(context);
    }

    @Override
    public String getHandlerName() {
        return "TerrainEffectHandler";
    }
}