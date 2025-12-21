package org.elementarclash.units.strategy.attack;

import org.elementarclash.game.Game;
import org.elementarclash.units.Unit;

import java.util.List;

/**
 * Strategy pattern for unit attack behavior.
 * Encapsulates targeting logic, range validation, and target selection.
 * <p>
 * Design Pattern: Strategy (GoF #5)
 * Why: Different units have different attack patterns (melee, ranged, area).
 *      Strategy pattern allows switching attack algorithms without modifying Unit classes.
 * <p>
 * Implementations:
 * - MeleeAttackStrategy: Range 1, adjacent cells only
 * - RangedAttackStrategy: Range 3-4, line of sight check
 * - AreaAttackStrategy: Decorator wrapping base strategy, hits multiple targets
 */
public interface AttackStrategy {

    boolean canAttack(Game game, Unit attacker, Unit target);

    List<Unit> getValidTargets(Game game, Unit attacker);

    default int calculateBaseDamage(Unit attacker, Unit target) {
        return attacker.getBaseStats().attack();
    }

    int getAttackRange(Unit unit);
}
