package org.elementarclash.util;

/**
 * Utility for distance calculations between positions.
 */
public final class PositionDistance {

    private PositionDistance() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Calculates Euclidean distance between two positions.
     */
    public static double euclidean(Position from, Position to) {
        int dx = from.x() - to.x();
        int dy = from.y() - to.y();
        return Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * Calculates Manhattan distance (taxicab distance) between two positions.
     */
    public static int manhattan(Position from, Position to) {
        return Math.abs(from.x() - to.x()) + Math.abs(from.y() - to.y());
    }

    /**
     * Checks if target is within given range using Euclidean distance.
     */
    public static boolean isInRange(Position from, Position to, int range) {
        return euclidean(from, to) <= range;
    }
}
