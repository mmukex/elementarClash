package units;

import faction.Faction;
import util.Position;

/**
 * Abstract base class for all units in ElementarClash.
 * Defines common properties and behavior for all unit types.
 *
 * Design Pattern: Factory Method creates instances of Unit subclasses.
 */
public abstract class Unit {
    private final String id;
    private final String name;
    private final Faction faction;
    private final UnitType type;
    private final UnitStats baseStats;

    private int currentHealth;
    private Position position;
    private boolean hasActed;

    protected Unit(String id, String name, Faction faction, UnitType type, UnitStats stats) {
        this.id = id;
        this.name = name;
        this.faction = faction;
        this.type = type;
        this.baseStats = stats;
        this.currentHealth = stats.maxHealth();
        this.hasActed = false;
    }

    // ========== Getters ==========
    public String getId() { return id; }
    public String getName() { return name; }
    public Faction getFaction() { return faction; }
    public UnitType getType() { return type; }
    public UnitStats getBaseStats() { return baseStats; }
    public int getCurrentHealth() { return currentHealth; }
    public Position getPosition() { return position; }
    public boolean hasActed() { return hasActed; }

    public boolean isAlive() { return currentHealth > 0; }

    // ========== State Management ==========
    public void setPosition(Position position) {
        this.position = position;
    }

    public void takeDamage(int damage) {
        currentHealth = Math.max(0, currentHealth - Math.max(0, damage));
    }

    public void heal(int amount) {
        currentHealth = Math.min(baseStats.maxHealth(), currentHealth + amount);
    }

    public void resetTurn() {
        hasActed = false;
    }

    public void markAsActed() {
        hasActed = true;
    }

    // ========== Abstract Methods (Template Method Pattern) ==========
    /**
     * Returns the unit's special ability description.
     * Implemented by each concrete unit type.
     */
    public abstract String getSpecialAbility();

    /**
     * Hook method for terrain-specific bonuses.
     * Can be overridden by subclasses (will be used with Visitor pattern later).
     */
    public int getTerrainAttackBonus() {
        return 0;
    }

    public int getTerrainDefenseBonus() {
        return 0;
    }

    @Override
    public String toString() {
        return String.format("%s [%s] - HP: %d/%d, ATK: %d, DEF: %d",
                name, faction.getIcon(), currentHealth, baseStats.maxHealth(),
                baseStats.attack(), baseStats.defense());
    }
}