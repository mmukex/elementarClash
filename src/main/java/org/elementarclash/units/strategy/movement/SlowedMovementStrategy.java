package org.elementarclash.units.strategy.movement;

import org.elementarclash.battlefield.Terrain;
import org.elementarclash.game.Game;
import org.elementarclash.util.Position;

/**
 * Wrapper strategy for slowed units with reduced movement range.
 * Delegates terrain cost calculation to wrapped strategy but reduces effective movement range.
 * <p>
 * Design Pattern: Strategy (GoF #5) + Decorator concept
 * Why: Status effects like "Slow" temporarily modify movement behavior.
 * Wrapping existing strategy preserves faction-specific terrain costs while reducing range.
 * <p>
 * Integration: Used by State Pattern (#6 @crstmkt) when unit enters SlowedState.
 * The State switches the unit's movement strategy to this wrapper temporarily.
 * <p>
 * Effect: Reduces movement range by MOVEMENT_PENALTY (default: 1 point).
 * Example: Unit with 3 movement becomes 2 when slowed.
 */
public class SlowedMovementStrategy implements MovementStrategy {

    private static final int MOVEMENT_PENALTY = 1;

    private final MovementStrategy wrappedStrategy;
    private final int penalty;

    public SlowedMovementStrategy(MovementStrategy wrappedStrategy) {
        this(wrappedStrategy, MOVEMENT_PENALTY);
    }

    public SlowedMovementStrategy(MovementStrategy wrappedStrategy, int penalty) {
        this.wrappedStrategy = wrappedStrategy;
        this.penalty = penalty;
    }

    @Override
    public double calculateMovementCost(Terrain terrain) {
        return wrappedStrategy.calculateMovementCost(terrain);
    }

    @Override
    public boolean canMoveTo(Game game, Position currentPosition, Position targetPosition, int maxMovement) {
        int reducedMovement = Math.max(0, maxMovement - penalty);
        return wrappedStrategy.canMoveTo(game, currentPosition, targetPosition, reducedMovement);
    }

    @Override
    public double calculatePathCost(Game game, Position[] path) {
        return wrappedStrategy.calculatePathCost(game, path);
    }

    public MovementStrategy getWrappedStrategy() {
        return wrappedStrategy;
    }

    public int getPenalty() {
        return penalty;
    }
}
