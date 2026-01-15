package org.elementarclash.units.strategy.attack;

import org.elementarclash.game.Game;
import org.elementarclash.units.Unit;

import java.util.List;

/**
 * Melee attack strategy for close-range combat (typically range 1).
 * <p>
 * Design Pattern: Strategy (GoF #5)
 * Why: Different unit types have different attack patterns (melee vs ranged).
 * Strategy pattern encapsulates attack validation logic as interchangeable algorithm.
 * <p>
 * Used by: Inferno Warrior, Tide Guardian, Stone Golem, Wind Dancer, Wave Rider, Phoenix.
 * <p>
 * Melee units must be adjacent to their target to attack.
 * This creates tactical positioning requirements and vulnerability to ranged units.
 *
 * @author mmukex
 */
public class MeleeAttackStrategy implements AttackStrategy {

    @Override
    public boolean canAttack(Game game, Unit attacker, Unit target) {
        if (!attacker.isAlive() || !target.isAlive()) {
            return false;
        }

        if (attacker.getFaction() == target.getFaction()) {
            return false;
        }

        return attacker.getPosition().manhattanDistanceTo(target.getPosition()) <= getAttackRange(attacker);
    }

    @Override
    public int getAttackRange(Unit unit) {
        return unit.getBaseStats().range();
    }
}
