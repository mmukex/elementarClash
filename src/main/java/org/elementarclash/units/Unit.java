package org.elementarclash.units;

import lombok.Getter;
import lombok.Setter;
import org.elementarclash.battlefield.terraineffect.TerrainEffectResult;
import org.elementarclash.battlefield.terraineffect.TerrainVisitor;
import org.elementarclash.units.bonus.UnitDecorator;
import org.elementarclash.units.state.IdleState;
import org.elementarclash.units.state.UnitState;
import org.elementarclash.units.strategy.attack.AttackStrategy;
import org.elementarclash.units.strategy.attack.MeleeAttackStrategy;
import org.elementarclash.units.strategy.movement.GroundMovementStrategy;
import org.elementarclash.units.strategy.movement.MovementStrategy;
import org.elementarclash.util.Position;

import java.util.ArrayList;
import java.util.List;


/**
 * Abstract base class for all units in ElementarClash.
 * Defines common properties and behavior for all unit types.
 * <p>
 * Design Pattern: Factory Method creates instances of Unit subclasses.
 */
@Getter
@Setter
public abstract class Unit {
    public static final int MAX_ACTIONS_PER_TURN = 2;

    private final String id;
    private final String name;
    private final Faction faction;
    private final UnitType type;
    private final UnitStats baseStats;
    private final List<UnitDecorator> decorators;


    private int actionsThisTurn;
    private int currentHealth;
    private Position position;
    private MovementStrategy movementStrategy;
    private AttackStrategy attackStrategy;
    private UnitState currentState;

    protected Unit(String id, String name, Faction faction, UnitType type, UnitStats stats) {
        this.id = id;
        this.name = name;
        this.faction = faction;
        this.type = type;
        this.baseStats = stats;
        this.decorators = new ArrayList<>();

        this.actionsThisTurn = 0;
        this.currentHealth = stats.maxHealth();
        this.currentState = IdleState.getInstance();
    }

    public boolean isAlive() {
        return currentHealth > 0;
    }

    public void takeDamage(int damage) {
        currentHealth -= Math.max(0, damage);
        if (currentHealth <= 0) {
            currentHealth = 0;
            transitionToDead();
        }
    }

    public void heal(int amount) {
        currentHealth = Math.min(baseStats.maxHealth(), currentHealth + amount);
    }

    public void setCurrentHealth(int health) {
        this.currentHealth = Math.clamp(0, baseStats.maxHealth(), health);
    }

    public void incrementActionsThisTurn() {
        this.actionsThisTurn++;
    }

    public void decrementActionsThisTurn() {
        this.actionsThisTurn--;
    }

    /**
     * Reset on entering new turn
     */
    public void resetTurn() {
        this.actionsThisTurn = 0;

        // State Pattern
        currentState = currentState.transitionToIdle(this);
        currentState.onTurnEnd(this);
    }

    public void setState(UnitState newState) {
        this.currentState = newState;
    }

    /**
     *
     * Check if unit has actions left this turn.
     */
    public boolean hasNoActionsLeft() {
        return !currentState.hasActionsLeft(this);
    }

    /**
     * Transition to Moving state.
     * Called by MoveCommand.execute()
     */
    public void startMoving() {
        currentState = currentState.transitionToMoving(this);
    }

    /**
     * Transition to Attacking state.
     * Called by AttackCommand.execute()
     */
    public void startAttacking() {
        currentState = currentState.transitionToAttacking(this);
    }

    /**
     * Transition to Stunned state.
     * Called by Frost Mage ability
     */
    public void stun(int rounds) {
        currentState = currentState.transitionToStunned(this, rounds);
    }

    /**
     * Mark unit as dead.
     * Called when currentHealth reaches 0
     */
    private void transitionToDead() {
        currentState = currentState.transitionToDead(this);
    }

    public MovementStrategy getMovementStrategy() {
        if (movementStrategy == null) {
            movementStrategy = new GroundMovementStrategy(faction);
        }
        return movementStrategy;
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


    /**
     * Returns the unit's description.
     * Implemented by each concrete unit type.
     */
    public abstract String getDescription();


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

    // ===== DECORATOR PATTERN METHODS =====

    /**
     * Add a decorator to this unit.
     */
    public void addDecorator(UnitDecorator decorator) {
        decorators.add(decorator);
    }

    /**
     * Remove a specific decorator.
     */
    public void removeDecorator(UnitDecorator decorator) {
        decorators.remove(decorator);
    }

    /**
     * Remove all decorators of a specific class.
     */
    public void removeDecoratorsOfType(Class<? extends UnitDecorator> decoratorClass) {
        decorators.removeIf(decoratorClass::isInstance);
    }

    /**
     * Remove all expired decorators (e.g., ability buffs).
     */
    public void removeExpiredDecorators() {
        decorators.removeIf(UnitDecorator::isExpired);
    }

    /**
     * Get all decorators (for debugging/UI).
     */
    public List<UnitDecorator> getDecorators() {
        return new ArrayList<>(decorators);
    }

    /**
     * Get total attack including all decorator bonuses.
     * REPLACES: baseStats.attack()
     */
    public int getAttack() {
        int attack = baseStats.attack();
        for (UnitDecorator decorator : decorators) {
            if (!decorator.isExpired()) {
                attack += decorator.getAttackBonus(this);
            }
        }
        return attack;
    }

    /**
     * Get total defense including all decorator bonuses.
     * REPLACES: baseStats.defense()
     */
    public int getDefense() {
        int defense = baseStats.defense();
        for (UnitDecorator decorator : decorators) {
            if (!decorator.isExpired()) {
                defense += decorator.getDefenseBonus(this);
            }
        }
        return defense;
    }

    /**
     * Get total movement including decorator bonuses (Slowed/Hastened).
     * REPLACES: baseStats.movement()
     */
    public int getMovement() {
        int movement = baseStats.movement();
        for (UnitDecorator decorator : decorators) {
            if (!decorator.isExpired()) {
                movement += decorator.getMovementBonus(this);
            }
        }
        return Math.max(1, movement); // Minimum 1 Movement
    }
}