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
 * Strategy pattern allows switching attack algorithms without modifying Unit classes.
 * <p>
 * Implementations:
 * - MeleeAttackStrategy: Range 1, adjacent cells only
 * - RangedAttackStrategy: Range 3-4, line of sight check
 *
 * @author mmukex
 */
public interface AttackStrategy {

    /**
     * Validates if attacker can target specific unit.
     * Checks range, line of sight, and special attack rules.
     *
     * @param game     game instance for position/obstacle checks
     * @param attacker attacking unit
     * @param target   target unit
     * @return true if attack is valid
     */
    boolean canAttack(Game game, Unit attacker, Unit target);

    /**
     * Calculates base damage before modifiers.
     * Default: returns attacker's attack stat.
     * Can be overridden for special damage calculations.
     *
     * @param attacker attacking unit
     * @param target   target unit
     * @return base damage value
     */
    default int calculateBaseDamage(Unit attacker, Unit target) {
        return attacker.getBaseStats().attack();
    }

    /**
     * Returns attack range of unit with this strategy.
     * Melee: 1, Ranged: 3-4, varies by unit type.
     *
     * @param unit unit to get range for
     * @return attack range in cells
     */
    int getAttackRange(Unit unit);
}
