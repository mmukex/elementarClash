package util;

/**
 * Immutable value object representing a position on the 10x10 battlefield grid.
 *
 * Uses Java Record for automatic implementation of:
 * - equals() / hashCode() (important for Map keys)
 * - toString()
 * - Getters (x(), y())
 *
 * @param x X-coordinate (0-9)
 * @param y Y-coordinate (0-9)
 */
public record Position(int x, int y) {

    /**
     * Compact constructor validates coordinates are within battlefield bounds.
     */
    public Position {
        if (x < 0 || x >= 10 || y < 0 || y >= 10) {
            throw new IllegalArgumentException(
                    "Position out of bounds: (" + x + ", " + y + "). Must be 0-9."
            );
        }
    }

    /**
     * Calculates Euclidean distance to another position.
     * Used for range checks (can unit attack target?).
     *
     * @param other Target position
     * @return Distance as double
     */
    public double distanceTo(Position other) {
        int dx = x - other.x;
        int dy = y - other.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * Calculates Manhattan distance (grid-based movement).
     * Used for movement costs (how many tiles to move?).
     *
     * @param other Target position
     * @return Manhattan distance (|dx| + |dy|)
     */
    public int manhattanDistanceTo(Position other) {
        return Math.abs(x - other.x) + Math.abs(y - other.y);
    }

    /**
     * Checks if this position is adjacent to another (horizontally/vertically).
     * Used for melee attacks and synergy bonuses.
     *
     * @param other Target position
     * @return true if positions are neighbors
     */
    public boolean isAdjacentTo(Position other) {
        return manhattanDistanceTo(other) == 1;
    }

    /**
     * Checks if position is within range (including diagonals).
     * Used for ranged attacks.
     *
     * @param other Target position
     * @param range Maximum range
     * @return true if other is within range
     */
    public boolean isInRange(Position other, int range) {
        return distanceTo(other) <= range;
    }

    /**
     * Returns new Position moved in a direction.
     * Useful for movement validation.
     *
     * @param dx Delta X (-1, 0, or 1)
     * @param dy Delta Y (-1, 0, or 1)
     * @return New Position or null if out of bounds
     */
    public Position move(int dx, int dy) {
        int newX = x + dx;
        int newY = y + dy;

        if (newX < 0 || newX >= 10 || newY < 0 || newY >= 10) {
            return null; // Out of bounds
        }

        return new Position(newX, newY);
    }

    /**
     * Returns all adjacent positions (N, E, S, W).
     * Used for area-of-effect abilities.
     *
     * @return Array of neighboring positions (2-4 positions depending on edges)
     */
    public Position[] getAdjacentPositions() {
        return new Position[]{
                move(0, -1),  // North
                move(1, 0),   // East
                move(0, 1),   // South
                move(-1, 0)   // West
        };
    }

    /**
     * Returns all positions within range (square area).
     *
     * @param range Range radius
     * @return Array of positions within range
     */
    public Position[] getPositionsInRange(int range) {
        var positions = new java.util.ArrayList<Position>();

        for (int dx = -range; dx <= range; dx++) {
            for (int dy = -range; dy <= range; dy++) {
                Position pos = move(dx, dy);
                if (pos != null && distanceTo(pos) <= range) {
                    positions.add(pos);
                }
            }
        }

        return positions.toArray(new Position[0]);
    }

    /**
     * Human-readable string format for debugging.
     * @return "(x, y)" format
     */
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}