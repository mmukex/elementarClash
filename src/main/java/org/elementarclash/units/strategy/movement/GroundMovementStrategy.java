package org.elementarclash.units.strategy.movement;

import org.elementarclash.battlefield.Terrain;
import org.elementarclash.faction.Faction;
import org.elementarclash.game.Game;
import org.elementarclash.util.Position;

/**
 * Ground-based movement strategy with faction-specific terrain modifiers.
 * Units pay full terrain movement costs based on terrain type and faction bonuses.
 * <p>
 * Design Pattern: Strategy (GoF #5)
 * Why: Different factions have unique terrain affinities (Fire on Lava, Water on Ice).
 * Strategy pattern encapsulates faction-specific movement rules as interchangeable algorithms.
 * <p>
 * Used by: Fire, Water, Earth factions (all non-flying units).
 * <p>
 * Faction-specific modifiers:
 * - Fire: Lava cost 1.0 (faster), Ice cost 2.0 (slower)
 * - Water: Ice cost 1.0 (faster), Lava cost 3.0 (slower)
 * - Earth: Stone cost ×0.67 (faster on rocky terrain)
 */
public class GroundMovementStrategy implements MovementStrategy {

    private static final double FIRE_LAVA_COST = 1.0;
    private static final double FIRE_ICE_COST = 2.0;
    private static final double WATER_ICE_COST = 1.0;
    private static final double WATER_LAVA_COST = 3.0;
    private static final double EARTH_STONE_MULTIPLIER = 0.67;

    private final Faction faction;

    public GroundMovementStrategy(Faction faction) {
        this.faction = faction;
    }

    @Override
    public double calculateMovementCost(Terrain terrain) {
        double baseCost = terrain.getMovementCost();

        return switch (faction) {
            case FIRE -> applyFireModifiers(terrain, baseCost);
            case WATER -> applyWaterModifiers(terrain, baseCost);
            case EARTH -> applyEarthModifiers(terrain, baseCost);
            default -> baseCost;
        };
    }

    private double applyFireModifiers(Terrain terrain, double baseCost) {
        return switch (terrain) {
            case LAVA -> FIRE_LAVA_COST;
            case ICE -> FIRE_ICE_COST;
            default -> baseCost;
        };
    }

    private double applyWaterModifiers(Terrain terrain, double baseCost) {
        return switch (terrain) {
            case ICE -> WATER_ICE_COST;
            case LAVA -> WATER_LAVA_COST;
            default -> baseCost;
        };
    }

    private double applyEarthModifiers(Terrain terrain, double baseCost) {
        return switch (terrain) {
            case STONE -> baseCost * EARTH_STONE_MULTIPLIER;
            default -> baseCost;
        };
    }

    @Override
    public boolean canMoveTo(Game game, Position currentPosition, Position targetPosition, int maxMovement) {
        if (game.isPositionOccupied(targetPosition)) {
            return false;
        }

        Terrain targetTerrain = game.getTerrainAt(targetPosition);
        int manhattanDistance = currentPosition.manhattanDistanceTo(targetPosition);
        double terrainCost = calculateMovementCost(targetTerrain);
        double totalCost = manhattanDistance * terrainCost;

        return totalCost <= maxMovement;
    }
}
