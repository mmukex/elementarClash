package org.elementarclash.units.strategy.movement;

import org.elementarclash.battlefield.Terrain;
import org.elementarclash.game.Game;
import org.elementarclash.util.Position;

/**
 * Strategy pattern for unit movement behavior.
 * Encapsulates terrain-based movement cost calculation and path validation.
 * <p>
 * Design Pattern: Strategy (GoF #5)
 * Why: Different factions have unique movement rules (Air flies, Water glides on ice, Fire on lava).
 *      Strategy pattern allows switching movement algorithms without modifying Unit classes.
 * <p>
 * Implementations:
 * - GroundMovementStrategy: Pays terrain costs with faction-specific modifiers
 * - FlyingMovementStrategy: Ignores terrain (used by Air faction and Phoenix)
 * - SpecialMovementStrategy: Hybrid with special terrain affinity (Wave Rider on ice)
 */
public interface MovementStrategy {

    double calculateMovementCost(Terrain terrain);

    boolean canMoveTo(Game game, Position currentPosition, Position targetPosition, int maxMovement);

    default double calculatePathCost(Game game, Position[] path) {
        double totalCost = 0.0;
        for (int i = 1; i < path.length; i++) {
            Terrain terrain = game.getBattlefield().getTerrainAt(path[i]);
            totalCost += calculateMovementCost(terrain);
        }
        return totalCost;
    }
}
