package org.elementarclash.game.combat;

import org.elementarclash.game.Game;
import org.elementarclash.game.combat.handler.*;
import org.elementarclash.units.Unit;

/**
<<<<<<< HEAD
 * Calculates combat damage using Chain of Responsibility Pattern.
 *
 * Chain: Base → Faction → Terrain → Synergy → Defense
 *
 * Integration Points:
 * - @mmukex Strategy Pattern: BaseDamageHandler uses AttackStrategy
 * - @mmukex Visitor Pattern: TerrainEffectHandler uses TerrainVisitorFactory
 * - @crstmkt Decorator Pattern: SynergyBonusHandler uses Unit decorators (Week 4)
 *
 * @author @crstmkt (refactored for Chain of Responsibility)
=======
 * Calculates combat damage considering base attack, terrain effects, and defense.
 * Uses Strategy Pattern (attack calculation) and Visitor Pattern (terrain effects).
 *
 * TODO: crstmkt - Chain of Responsibility - Refactor to use damage modifier chain
>>>>>>> master
 */
public class DamageCalculator {

    private final DamageHandler handlerChain;

    public DamageCalculator() {
        // Build the chain (order matters!)
        this.handlerChain = new BaseDamageHandler();
        handlerChain
                .setNext(new FactionAdvantageHandler())
                .setNext(new TerrainEffectHandler())      // Integration with @mmukex Visitor!
                .setNext(new SynergyBonusHandler())       // Integration with @crstmkt Decorator (Week 4)
                .setNext(new DefenseCalculationHandler());
    }

    /**
     * Calculate damage from attacker to target.
     *
     * @param attacker attacking unit
     * @param target defending unit
     * @param game game state (for terrain lookup, adjacent units)
     * @return detailed damage result
     */
    public DamageResult calculateDamage(Unit attacker, Unit target, Game game) {
        DamageContext context = new DamageContext(attacker, target, game);
        // TODO: crstmkt - Decorator Pattern

        // Execute the chain
        handlerChain.handle(context);

        // Optional: print calculation log for debugging
        // context.printCalculationLog();

        return context.toResult();
    }
}