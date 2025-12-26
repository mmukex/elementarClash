package org.elementarclash.units.strategy.movement;

import org.elementarclash.battlefield.Terrain;
import org.elementarclash.game.Game;
import org.elementarclash.util.Position;

/**
 * Flying movement strategy that ignores terrain penalties.
 * Flying units treat all terrain as cost 1.0 for maximum mobility.
 * <p>
 * Design Pattern: Strategy (GoF #5)
 * Why: Air faction has unique mobility advantage (flying over obstacles).
 * Strategy pattern allows swapping movement algorithms without modifying Unit class.
 * <p>
 * Used by: Phoenix (Fire), All Air faction units (Wind Dancer, Storm Caller, Sky Guardian).
 * <p>
 * Flying advantages:
 * - No terrain penalties (forest, ice, lava all cost 1.0)
 * - Can traverse difficult terrain without slowdown
 * - Effective movement range equals base movement stat
 */
public class FlyingMovementStrategy implements MovementStrategy {

    private static final double FLYING_TERRAIN_COST = 1.0;

    @Override
    public double calculateMovementCost(Terrain terrain) {
        return FLYING_TERRAIN_COST;
    }

    @Override
    public boolean canMoveTo(Game game, Position currentPosition, Position targetPosition, int maxMovement) {
        if (game.isPositionOccupied(targetPosition)) {
            return false;
        }

        int distance = currentPosition.manhattanDistanceTo(targetPosition);
        return distance <= maxMovement;
    }
}
