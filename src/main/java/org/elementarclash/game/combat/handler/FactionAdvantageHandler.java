package org.elementarclash.game.combat.handler;

import org.elementarclash.game.combat.DamageContext;
import org.elementarclash.units.Faction;

import java.util.Map;

/**
 * Apply faction advantage multipliers.
 * <p>
 * Faction Advantages (from README):
 * - Fire vs Earth: +25% damage
 * - Fire vs Water: -25% damage
 * - Water vs Fire: +25% damage
 * - Water vs Earth: -25% damage
 * - Earth vs Water: +25% damage
 * - Earth vs Air: -25% damage
 * - Air vs Earth: +25% damage
 * - Air vs Fire: -25% damage
 * <p>
 */
public class FactionAdvantageHandler extends DamageHandler {

    // Faction advantage matrix: (attacker, defender) -> multiplier
    private static final Map<Faction, Map<Faction, Double>> ADVANTAGE_MATRIX = Map.of(
            Faction.FIRE, Map.of(
                    Faction.EARTH, 1.25,  // Fire strong vs Earth
                    Faction.WATER, 0.75   // Fire weak vs Water
            ),
            Faction.WATER, Map.of(
                    Faction.FIRE, 1.25,   // Water strong vs Fire
                    Faction.EARTH, 0.75   // Water weak vs Earth
            ),
            Faction.EARTH, Map.of(
                    Faction.WATER, 1.25,  // Earth strong vs Water
                    Faction.AIR, 0.75     // Earth weak vs Air
            ),
            Faction.AIR, Map.of(
                    Faction.EARTH, 1.25,  // Air strong vs Earth
                    Faction.FIRE, 0.75    // Air weak vs Fire
            )
    );

    @Override
    public void handle(DamageContext context) {
        Faction attackerFaction = context.getAttacker().getFaction();
        Faction defenderFaction = context.getTarget().getFaction();

        double multiplier = ADVANTAGE_MATRIX
                .getOrDefault(attackerFaction, Map.of())
                .getOrDefault(defenderFaction, 1.0);

        if (multiplier != 1.0) {
            context.applyFactionMultiplier(multiplier);
        }

        // Pass to next handler
        super.handle(context);
    }
}