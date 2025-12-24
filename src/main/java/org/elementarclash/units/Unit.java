package org.elementarclash.units;

import lombok.Getter;
import lombok.Setter;
import org.elementarclash.battlefield.visitor.TerrainEffectResult;
import org.elementarclash.battlefield.visitor.TerrainVisitor;
import org.elementarclash.faction.Faction;
import org.elementarclash.units.strategy.attack.AttackStrategy;
import org.elementarclash.units.strategy.attack.MeleeAttackStrategy;
import org.elementarclash.units.strategy.movement.GroundMovementStrategy;
import org.elementarclash.units.strategy.movement.MovementStrategy;
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
    private boolean movedThisTurn;
    private boolean attackedThisTurn;

    private MovementStrategy movementStrategy;
    private AttackStrategy attackStrategy;

    protected Unit(String id, String name, Faction faction, UnitType type, UnitStats stats) {
        this.id = id;
        this.name = name;
        this.faction = faction;
        this.type = type;
        this.baseStats = stats;
        this.currentHealth = stats.maxHealth();
        this.movedThisTurn = false;
        this.attackedThisTurn = false;
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
        movedThisTurn = false;
        attackedThisTurn = false;
    }

    @Deprecated
    public void markAsActed() {
        // Deprecated: Use markMovedThisTurn() or markAttackedThisTurn()
        movedThisTurn = true;
        attackedThisTurn = true;
    }

    public void markMovedThisTurn() {
        movedThisTurn = true;
    }

    public void markAttackedThisTurn() {
        attackedThisTurn = true;
    }

    public void clearMovedThisTurn() {
        movedThisTurn = false;
    }

    public void clearAttackedThisTurn() {
        attackedThisTurn = false;
    }

    public boolean hasMovedThisTurn() {
        return movedThisTurn;
    }

    public boolean hasAttackedThisTurn() {
        return attackedThisTurn;
    }

    public boolean canAct() {
        return !movedThisTurn || !attackedThisTurn;
    }

    public MovementStrategy getMovementStrategy() {
        if (movementStrategy == null) {
            movementStrategy = new GroundMovementStrategy(faction);
        }
        return movementStrategy;
    }

    protected void setMovementStrategy(MovementStrategy strategy) {
        this.movementStrategy = strategy;
    }

    public AttackStrategy getAttackStrategy() {
        if (attackStrategy == null) {
            attackStrategy = new MeleeAttackStrategy();
        }
        return attackStrategy;
    }

    protected void setAttackStrategy(AttackStrategy strategy) {
        this.attackStrategy = strategy;
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


    public TerrainEffectResult accept(TerrainVisitor visitor) {
        return switch (faction) {
            case FIRE -> visitor.visitFireUnit(this);
            case WATER -> visitor.visitWaterUnit(this);
            case EARTH -> visitor.visitEarthUnit(this);
            case AIR -> visitor.visitAirUnit(this);
        };
    }

    @Override
    public String toString() {
        return String.format("%s [%s] - HP: %d/%d, ATK: %d, DEF: %d",
                name, faction.getIcon(), currentHealth, baseStats.maxHealth(),
                baseStats.attack(), baseStats.defense());
    }
}