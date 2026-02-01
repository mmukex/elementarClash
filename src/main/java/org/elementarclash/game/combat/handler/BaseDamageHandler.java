package org.elementarclash.game.combat.handler;

import org.elementarclash.game.combat.DamageContext;

/**
 * First handler: Extract base attack from attacker.
 * Uses @mmukex Strategy Pattern (AttackStrategy.calculateBaseDamage).
 */
public class BaseDamageHandler extends DamageHandler {

    @Override
    public void handle(DamageContext context) {
        // Integration with @mmukex Strategy Pattern!
        int baseDamage = context.getAttacker()
                .getAttackStrategy()
                .calculateBaseDamage(context.getAttacker(), context.getTarget());

        context.setBaseDamage(baseDamage);

        // Pass to next handler
        super.handle(context);
    }
}