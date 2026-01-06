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

    private static Position moveIfValid(Position pos, int dx, int dy) {
        int newX = pos.x() + dx;
        int newY = pos.y() + dy;

        if (newX < 0 || newX >= 10 || newY < 0 || newY >= 10) {
            return null;
        }

        return new Position(newX, newY);
    }
}
