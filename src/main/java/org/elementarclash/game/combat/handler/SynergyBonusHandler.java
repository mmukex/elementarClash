package org.elementarclash.game.combat.handler;

import org.elementarclash.game.combat.DamageContext;

/**
 * Apply synergy bonuses from adjacent units.
 * Integration Point: Will use @crstmkt Decorator Pattern (Pattern #4)
 *
 */
public class SynergyBonusHandler extends DamageHandler {

    @Override
    public void handle(DamageContext context) {
        // Note: Terrain bonus is already added by TerrainEffectHandler
        // Only add non-terrain bonuses here (synergies, abilities)
        int synergyBonus = context.getAttacker().getDecorators().stream()
                .filter(d -> !d.isExpired())
                .mapToInt(d -> d.getAttackBonus(context.getAttacker()))
                .sum();

        if (synergyBonus != 0) {
            context.addSynergyBonus(synergyBonus);
        }

        // Pass to next handler
        super.handle(context);
    }
}