package org.elementarclash.util;

/**
 * Immutable value object representing a position on the 10x10 battlefield grid.
 *
 * @param x X-coordinate (0-9)
 * @param y Y-coordinate (0-9)
 */
public record Position(int x, int y) {

    public Position {
        if (x < 0 || x >= 10 || y < 0 || y >= 10) {
            throw new IllegalArgumentException(
                    "Position out of bounds: (" + x + ", " + y + "). Must be 0-9."
            );
        }
    }

    public int manhattanDistanceTo(Position other) {
        return PositionDistance.manhattan(this, other);
    }

    public boolean isInRange(Position other, int range) {
        return PositionDistance.isInRange(this, other, range);
    }

    public Position[] getAdjacentPositions() {
        return PositionNeighborhood.getAdjacent(this);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}