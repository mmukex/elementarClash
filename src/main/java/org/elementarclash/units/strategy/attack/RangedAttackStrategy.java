package org.elementarclash.units.strategy.attack;

import org.elementarclash.battlefield.Terrain;
import org.elementarclash.game.Game;
import org.elementarclash.units.Unit;
import org.elementarclash.util.Position;

import java.util.List;

/**
 * Ranged attack strategy for long-distance combat (range 3-4).
 * Includes line of sight validation - forest terrain blocks attacks unless unit has piercing ability.
 * <p>
 * Design Pattern: Strategy (GoF #5)
 * Why: Ranged units have complex targeting rules (range, line of sight, forest blocking).
 * Strategy pattern encapsulates ranged attack validation separate from melee logic.
 * <p>
 * Used by:
 * - Flame Archer (range 3, ignores forest)
 * - Frost Mage (range 4, affected by forest)
 * - Storm Caller (range 4, affected by forest)
 * - Sky Guardian (range 2, affected by forest)
 *
 * @author mmukex
 */
public class RangedAttackStrategy implements AttackStrategy {

    private final boolean ignoresForestDefense;

    public RangedAttackStrategy(boolean ignoresForestDefense) {
        this.ignoresForestDefense = ignoresForestDefense;
    }

    @Override
    public boolean canAttack(Game game, Unit attacker, Unit target) {
        if (!attacker.isAlive() || !target.isAlive()) {
            return false;
        }

        if (attacker.getFaction() == target.getFaction()) {
            return false;
        }

        int attackRange = attacker.getBaseStats().range();
        if (!attacker.getPosition().isInRange(target.getPosition(), attackRange)) {
            return false;
        }

        return hasLineOfSight(game, attacker.getPosition(), target.getPosition());
    }

    private boolean hasLineOfSight(Game game, Position from, Position to) {
        if (ignoresForestDefense) {
            return true;
        }

        Position[] pathPositions = getPathPositions(from, to);

        for (Position pos : pathPositions) {
            if (pos.equals(from) || pos.equals(to)) {
                continue;
            }

            Terrain terrain = game.getTerrainAt(pos);
            if (terrain == Terrain.FOREST) {
                return false;
            }
        }

        return true;
    }

    private Position[] getPathPositions(Position from, Position to) {
        int dx = to.x() - from.x();
        int dy = to.y() - from.y();
        int steps = Math.max(Math.abs(dx), Math.abs(dy));

        if (steps == 0) {
            return new Position[]{from};
        }

        Position[] path = new Position[steps + 1];
        for (int i = 0; i <= steps; i++) {
            int x = from.x() + (dx * i) / steps;
            int y = from.y() + (dy * i) / steps;
            path[i] = new Position(x, y);
        }

        return path;
    }

    @Override
    public List<Unit> getValidTargets(Game game, Unit attacker) {
        return game.getEnemiesOf(attacker.getFaction()).stream()
                .filter(target -> canAttack(game, attacker, target))
                .toList();
    }

    @Override
    public int getAttackRange(Unit unit) {
        return unit.getBaseStats().range();
    }
}
