package org.elementarclash.game.combat.handler;

import org.elementarclash.game.combat.DamageContext;

/**
 * Final handler: Calculate total damage after defense.
 * <p>
 * Formula: finalDamage = max(1, totalAttack - totalDefense)
 */
public class DefenseCalculationHandler extends DamageHandler {

    @Override
    public void handle(DamageContext context) {
        // Total attack = base + terrain + synergies
        int totalAttack = context.getTotalAttack();

        // target.getDefense() already includes all decorators (terrain + synergies)
        int totalDefense = context.getTarget().getDefense();

        context.setTotalDefense(totalDefense);

        // Final damage (minimum 1)
        int finalDamage = Math.max(1, totalAttack - totalDefense);
        context.setFinalDamage(finalDamage);

        // No next handler (end of chain)
        super.handle(context);
    }
}