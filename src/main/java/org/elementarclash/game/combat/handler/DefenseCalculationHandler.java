package org.elementarclash.game.combat.handler;

import org.elementarclash.game.combat.DamageContext;

/**
 * Final handler: Calculate total damage after defense.
 *
 * Formula: finalDamage = max(1, totalAttack - totalDefense)
 */
public class DefenseCalculationHandler extends DamageHandler {

    @Override
    public void handle(DamageContext context) {
        // Total attack = base + terrain + synergies
        int totalAttack = context.getTotalAttack();

        // Total defense = base stats + terrain bonus
        int baseDefense = context.getTarget().getBaseStats().defense();
        int totalDefense = baseDefense + context.getTerrainDefenseBonus();

        context.setTotalDefense(totalDefense);

        // Final damage (minimum 1)
        int finalDamage = Math.max(1, totalAttack - totalDefense);
        context.setFinalDamage(finalDamage);

        // No next handler (end of chain)
        super.handle(context);
    }

    @Override
    public String getHandlerName() {
        return "DefenseCalculationHandler";
    }
}