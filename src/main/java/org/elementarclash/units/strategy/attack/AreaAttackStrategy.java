package org.elementarclash.units.strategy.attack;

import org.elementarclash.game.Game;
import org.elementarclash.units.Unit;

import java.util.ArrayList;
import java.util.List;

/**
 * Area-of-effect attack strategy.
 * Targets primary enemy and all adjacent enemies.
 * <p>
 * Used by: Earthquake Titan (hits all adjacent enemies with 75% damage).
 * <p>
 * Decorator pattern: Wraps a base strategy (typically MeleeAttackStrategy)
 * and extends it to affect multiple targets around the primary target.
 */
public class AreaAttackStrategy implements AttackStrategy {

    private final AttackStrategy baseStrategy;
    private final double aoeDamageMultiplier;

    public AreaAttackStrategy(AttackStrategy baseStrategy, double aoeDamageMultiplier) {
        this.baseStrategy = baseStrategy;
        this.aoeDamageMultiplier = aoeDamageMultiplier;
    }

    @Override
    public boolean canAttack(Game game, Unit attacker, Unit target) {
        return baseStrategy.canAttack(game, attacker, target);
    }

    @Override
    public List<Unit> getValidTargets(Game game, Unit attacker) {
        return baseStrategy.getValidTargets(game, attacker);
    }

    /**
     * Returns all units affected by the area attack.
     * This includes the primary target AND adjacent enemies.
     */
    public List<Unit> getAffectedTargets(Game game, Unit attacker, Unit primaryTarget) {
        List<Unit> affected = new ArrayList<>();

        if (canAttack(game, attacker, primaryTarget)) {
            affected.add(primaryTarget);
        }

        List<Unit> adjacentUnits = game.getUnitsAdjacentTo(primaryTarget.getPosition());
        for (Unit unit : adjacentUnits) {
            if (unit.getFaction() != attacker.getFaction() && unit.isAlive()) {
                affected.add(unit);
            }
        }

        return affected;
    }

    @Override
    public int calculateBaseDamage(Unit attacker, Unit target) {
        int fullDamage = baseStrategy.calculateBaseDamage(attacker, target);
        return (int) (fullDamage * aoeDamageMultiplier);
    }

    @Override
    public int getAttackRange(Unit unit) {
        return baseStrategy.getAttackRange(unit);
    }
}
