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
 * Strategy pattern allows switching movement algorithms without modifying Unit classes.
 * <p>
 * Implementations:
 * - GroundMovementStrategy: Pays terrain costs with faction-specific modifiers
 * - FlyingMovementStrategy: Ignores terrain (used by Air faction and Phoenix)
 *
 * @author mmukex
 */
public interface MovementStrategy {

    /**
     * Calculates movement cost for crossing specific terrain.
     * Faction-specific bonuses applied here (e.g., Fire on Lava costs 1, Water costs 3).
     *
     * @param terrain terrain type to traverse
     * @return movement cost (typically 1-3)
     */
    double calculateMovementCost(Terrain terrain);

    /**
     * Validates if unit can reach target position within movement range.
     * Checks terrain costs, obstacles, and total distance.
     *
     * @param game            game instance for battlefield access
     * @param currentPosition starting position
     * @param targetPosition  destination position
     * @param maxMovement     maximum movement points available
     * @return true if move is valid
     */
    boolean canMoveTo(Game game, Position currentPosition, Position targetPosition, int maxMovement);

    /**
     * Calculates total movement cost for entire path.
     * Sums terrain costs from start to end.
     *
     * @param game game instance for terrain lookup
     * @param path array of positions from start to end
     * @return total movement cost
     */
    default double calculatePathCost(Game game, Position[] path) {
        double totalCost = 0.0;
        for (int i = 1; i < path.length; i++) {
            Terrain terrain = game.getTerrainAt(path[i]);
            totalCost += calculateMovementCost(terrain);
        }
        return totalCost;
    }
}
