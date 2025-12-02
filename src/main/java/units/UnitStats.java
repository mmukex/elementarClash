package units;

/**
 * Immutable value object holding unit statistics.
 * Used to configure units without exposing setters.
 */
public record UnitStats(
        int maxHealth,
        int attack,
        int defense,
        int movement,
        int range
) {
    public UnitStats {
        if (maxHealth <= 0 || attack < 0 || defense < 0 || movement < 0 || range < 0) {
            throw new IllegalArgumentException("Invalid unit stats");
        }
    }
}