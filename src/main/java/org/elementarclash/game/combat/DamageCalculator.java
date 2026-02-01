package org.elementarclash.game.combat;

import org.elementarclash.game.Game;
import org.elementarclash.game.combat.handler.*;
import org.elementarclash.units.Unit;

/**
 * Calculates combat damage using Chain of Responsibility Pattern.
 * <p>
 * Chain: Base → Faction → Terrain → Synergy → Defense
 * <p>
 * Integration Points:
 * - @mmukex Strategy Pattern: BaseDamageHandler uses AttackStrategy
 * - @mmukex Visitor Pattern: TerrainEffectHandler uses TerrainVisitorFactory
 * - @crstmkt Decorator Pattern: SynergyBonusHandler uses Unit decorators (Week 4)
 *
 * @author @crstmkt (refactored for Chain of Responsibility)
 */
public class DamageCalculator {

    private final DamageHandler handlerChain;

    public DamageCalculator() {
        // Build the chain (order matters!)
        this.handlerChain = new BaseDamageHandler();
        handlerChain
                .setNext(new FactionAdvantageHandler())
                .setNext(new TerrainEffectHandler())      // Integration with @mmukex Visitor!
                .setNext(new SynergyBonusHandler())       // Integration with @crstmkt Decorator
                .setNext(new DefenseCalculationHandler());
    }

    /**
     * Calculate damage from attacker to target.
     *
     * @param attacker attacking unit
     * @param target   defending unit
     * @param game     game state (for terrain lookup, adjacent units)
     * @return detailed damage result
     */
    public DamageResult calculateDamage(Unit attacker, Unit target, Game game) {
        DamageContext context = new DamageContext(attacker, target, game);

        handlerChain.handle(context);

        return context.toResult();
    }
}