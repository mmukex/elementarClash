package org.elementarclash.units.strategy.movement;

import org.elementarclash.battlefield.Terrain;
import org.elementarclash.game.Game;
import org.elementarclash.util.Position;

/**
 * Special movement for units with unique terrain affinity.
 * Decorator pattern: wraps a base strategy and applies special terrain cost reductions.
 * <p>
 * Example: Wave Rider can move through water/ice with reduced cost (1.0 instead of 1.2).
 * <p>
 * This hybrid strategy combines:
 * - Base strategy for most terrain (e.g., GroundMovementStrategy)
 * - Special reduced costs for specific terrain types
 */
public class SpecialMovementStrategy implements MovementStrategy {

    private final MovementStrategy baseStrategy;
    private final Terrain[] reducedCostTerrains;
    private final double reducedCost;

    public SpecialMovementStrategy(MovementStrategy baseStrategy,
                                   Terrain[] reducedCostTerrains,
                                   double reducedCost) {
        this.baseStrategy = baseStrategy;
        this.reducedCostTerrains = reducedCostTerrains;
        this.reducedCost = reducedCost;
    }

    @Override
    public double calculateMovementCost(Terrain terrain) {
        for (Terrain specialTerrain : reducedCostTerrains) {
            if (terrain == specialTerrain) {
                return reducedCost;
            }
        }

        return baseStrategy.calculateMovementCost(terrain);
    }

    @Override
    public boolean canMoveTo(Game game, Position currentPosition, Position targetPosition, int maxMovement) {
        if (game.isPositionOccupied(targetPosition)) {
            return false;
        }

        Terrain targetTerrain = game.getBattlefield().getTerrainAt(targetPosition);
        int manhattanDistance = currentPosition.manhattanDistanceTo(targetPosition);
        double totalCost = manhattanDistance * calculateMovementCost(targetTerrain);

        return totalCost <= maxMovement;
    }
}
