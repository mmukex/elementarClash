package org.elementarclash.units;

import lombok.Getter;
import lombok.Setter;
import org.elementarclash.faction.Faction;
import org.elementarclash.util.Position;

import java.util.List;

/**
 * Abstract base class for all units in ElementarClash.
 * Defines common properties and behavior for all unit types.
 * <p>
 * Design Pattern: Factory Method creates instances of Unit subclasses.
 * Design Pattern: Composite - Unit is Leaf in UnitComponent hierarchy.
 */
@Getter
public abstract class Unit implements UnitComponent {
    private final String id;
    private final String name;
    private final Faction faction;
    private final UnitType type;
    private final UnitStats baseStats;

    private int currentHealth;
    @Setter
    private Position position;
    private boolean acted;

    protected Unit(String id, String name, Faction faction, UnitType type, UnitStats stats) {
        this.id = id;
        this.name = name;
        this.faction = faction;
        this.type = type;
        this.baseStats = stats;
        this.currentHealth = stats.maxHealth();
        this.acted = false;
    }

    public boolean isAlive() {
        return currentHealth > 0;
    }

    public void takeDamage(int damage) {
        currentHealth = Math.max(0, currentHealth - Math.max(0, damage));
    }

    public void heal(int amount) {
        currentHealth = Math.min(baseStats.maxHealth(), currentHealth + amount);
    }

    public void resetTurn() {
        acted = false;
    }

    public void markAsActed() {
        acted = true;
    }

    @Override
    public List<Unit> getAllUnits() {
        return List.of(this);
    }

    @Override
    public int getTotalHealth() {
        return currentHealth;
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