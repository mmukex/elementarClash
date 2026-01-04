package org.elementarclash.game.combat.handler;

import org.elementarclash.game.combat.DamageContext;

/**
 * Apply synergy bonuses from adjacent units.
 *
 * Integration Point: Will use @crstmkt Decorator Pattern (Pattern #4)
 * TODO: Implement after Decorator Pattern is done (Week 4)
 */
public class SynergyBonusHandler extends DamageHandler {

    @Override
    public void handle(DamageContext context) {
        // TODO (Week 4): After Decorator Pattern implemented
        //
        // int totalBonus = 0;
        // for (UnitDecorator decorator : context.getAttacker().getDecorators()) {
        //     totalBonus += decorator.getAttackBonus(context.getAttacker());
        // }
        //
        // if (totalBonus != 0) {
        //     context.addSynergyBonus(totalBonus);
        // }

        // For now, calculate simple adjacent unit bonus manually
        int adjacentBonus = calculateAdjacentBonus(context);
        if (adjacentBonus != 0) {
            context.addSynergyBonus(adjacentBonus);
        }

        // Pass to next handler
        super.handle(context);
    }

    /**
     * Temporary implementation until Decorator Pattern is done.
     * README: "Benachbarte Feuer-Einheiten gewähren einander +1 Angriff"
     */
    private int calculateAdjacentBonus(DamageContext context) {
        long adjacentAllies = context.getGame()
                .getUnitsAdjacentTo(context.getAttacker().getPosition())
                .stream()
                .filter(u -> u.getFaction() == context.getAttacker().getFaction())
                .filter(u -> u.isAlive())
                .count();

        // Fire faction: +1 attack per adjacent ally
        if (context.getAttacker().getFaction() == org.elementarclash.faction.Faction.FIRE) {
            return (int) adjacentAllies;
        }

        return 0;
    }

    @Override
    public String getHandlerName() {
        return "SynergyBonusHandler";
    }
}