package org.elementarclash.util;

/**
 * Utility for position neighborhood and range operations.
 */
public final class PositionNeighborhood {

    private PositionNeighborhood() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Returns all adjacent positions (N, E, S, W - cardinal directions only).
     * Does not include diagonal positions.
     */
    public static Position[] getAdjacent(Position pos) {
        return new Position[]{
                moveIfValid(pos, 0, -1),  // North
                moveIfValid(pos, 1, 0),   // East
                moveIfValid(pos, 0, 1),   // South
                moveIfValid(pos, -1, 0)   // West
        };
    }

    /**
     * Checks if two positions are adjacent (horizontally or vertically).
     * Does not include diagonal positions.
     */
    public static boolean areAdjacent(Position p1, Position p2) {
        return PositionDistance.manhattan(p1, p2) == 1;
    }

    /**
     * Returns all positions within given range (Euclidean distance).
     */
    public static Position[] getInRange(Position center, int range) {
        var positions = new java.util.ArrayList<Position>();

        for (int dx = -range; dx <= range; dx++) {
            for (int dy = -range; dy <= range; dy++) {
                Position pos = moveIfValid(center, dx, dy);
                if (pos != null && PositionDistance.euclidean(center, pos) <= range) {
                    positions.add(pos);
                }
            }
        }

        return positions.toArray(new Position[0]);
    }

    private static Position moveIfValid(Position pos, int dx, int dy) {
        int newX = pos.x() + dx;
        int newY = pos.y() + dy;

        if (newX < 0 || newX >= 10 || newY < 0 || newY >= 10) {
            return null;
        }

        return new Position(newX, newY);
    }
}
