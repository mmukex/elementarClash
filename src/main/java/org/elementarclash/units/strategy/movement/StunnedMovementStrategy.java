package org.elementarclash.units.strategy.movement;

import org.elementarclash.battlefield.Terrain;
import org.elementarclash.game.Game;
import org.elementarclash.util.Position;

/**
 * Movement strategy for stunned units that cannot move.
 * Always returns false for movement validation regardless of distance or terrain.
 * <p>
 * Design Pattern: Strategy (GoF #5) + Null Object Pattern
 * Why: Stunned state completely disables movement but unit still needs a valid strategy.
 * This strategy provides "no movement" behavior without null checks or special cases.
 * <p>
 * Integration: Used by State Pattern (#6 @crstmkt) when unit enters StunnedState.
 * The State replaces the unit's movement strategy with this strategy temporarily.
 * <p>
 * Effect: Unit cannot move at all (canMoveTo always returns false).
 * Terrain costs are still calculated for consistency but have no practical effect.
 * <p>
 * Note: Stores original strategy for restoration when stun effect ends.
 */
public class StunnedMovementStrategy implements MovementStrategy {

    private final MovementStrategy originalStrategy;

    public StunnedMovementStrategy(MovementStrategy originalStrategy) {
        this.originalStrategy = originalStrategy;
    }

    @Override
    public double calculateMovementCost(Terrain terrain) {
        return originalStrategy.calculateMovementCost(terrain);
    }

    @Override
    public boolean canMoveTo(Game game, Position currentPosition, Position targetPosition, int maxMovement) {
        return false;
    }

    @Override
    public double calculatePathCost(Game game, Position[] path) {
        return Double.POSITIVE_INFINITY;
    }

    public MovementStrategy getOriginalStrategy() {
        return originalStrategy;
    }
}
