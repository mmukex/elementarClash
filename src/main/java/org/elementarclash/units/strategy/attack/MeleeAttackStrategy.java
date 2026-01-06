package org.elementarclash.units.strategy.attack;

import org.elementarclash.game.Game;
import org.elementarclash.units.Unit;

import java.util.List;

/**
 * Melee attack strategy for close-range combat (range 1, adjacent cells only).
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

    private static final int MELEE_RANGE = 1;

    @Override
    public boolean canAttack(Game game, Unit attacker, Unit target) {
        if (!attacker.isAlive() || !target.isAlive()) {
            return false;
        }

        if (attacker.getFaction() == target.getFaction()) {
            return false;
        }

        return attacker.getPosition().manhattanDistanceTo(target.getPosition()) <= MELEE_RANGE;
    }

    @Override
    public List<Unit> getValidTargets(Game game, Unit attacker) {
        return game.getEnemiesOf(attacker.getFaction()).stream()
                .filter(target -> canAttack(game, attacker, target))
                .toList();
    }

    @Override
    public int getAttackRange(Unit unit) {
        return MELEE_RANGE;
    }
}
