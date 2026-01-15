package org.elementarclash.units;

import lombok.Getter;
import lombok.Setter;
import org.elementarclash.battlefield.visitor.TerrainEffectResult;
import org.elementarclash.battlefield.visitor.TerrainVisitor;
import org.elementarclash.faction.Faction;
import org.elementarclash.game.phase.PlayerTurnPhase;
import org.elementarclash.units.state.IdleState;
import org.elementarclash.units.state.UnitState;
import org.elementarclash.units.strategy.attack.AttackStrategy;
import org.elementarclash.units.strategy.attack.MeleeAttackStrategy;
import org.elementarclash.units.strategy.movement.GroundMovementStrategy;
import org.elementarclash.units.strategy.movement.MovementStrategy;
import org.elementarclash.util.Position;
import org.elementarclash.units.decorator.UnitDecorator;
import java.util.ArrayList;
import java.util.List;


/**
 * Abstract base class for all units in ElementarClash.
 * Defines common properties and behavior for all unit types.
 * <p>
 * Design Pattern: Factory Method creates instances of Unit subclasses.
 */
@Getter
public abstract class Unit {
    private final String id;
    private final String name;
    private final Faction faction;
    private final UnitType type;
    private final UnitStats baseStats;

    private int currentHealth;
    @Setter
    private Position position;

    //Durch STATE Pattern ersetzt
    // TODO: crstmkt - State Pattern - Replace boolean flags with UnitTurnState - DONE
    // private boolean movedThisTurn;
    // private boolean attackedThisTurn;
    @Setter
    public int actionsThisTurn = 0;
    public final int maxActionsPerTurn = 2;

    @Setter
    private MovementStrategy movementStrategy;
    private AttackStrategy attackStrategy;

    // TODO: crstmkt - Decorator Pattern - Add buff/debuff system here - DONE

    private UnitState currentState;
    private final List<UnitDecorator> decorators = new ArrayList<>();

    protected Unit(String id, String name, Faction faction, UnitType type, UnitStats stats) {
        this.id = id;
        this.name = name;
        this.faction = faction;
        this.type = type;
        this.baseStats = stats;
        this.currentHealth = stats.maxHealth();
        // this.movedThisTurn = false;
        // this.attackedThisTurn = false;
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
        this.currentHealth = Math.max(0, Math.min(baseStats.maxHealth(), health));
    }

    public void incrementActionsThisTurn(){
        this.actionsThisTurn++;
    }

    public void decrementActionsThisTurn(){
        this.actionsThisTurn--;
    }

    /**
     * Reset on entering new turn
     */
    public void resetTurn() {
        // movedThisTurn = false;
        // attackedThisTurn = false;
        this.actionsThisTurn = 0;

        // State Pattern
        currentState = currentState.transitionToIdle(this);
        currentState.onTurnEnd(this);
    }

    public void setState(UnitState newState) {
        this.currentState = newState;
    }

    /**
     * Check if unit can move based on current state.
     * Replaces: !movedThisTurn check
     */
//    public boolean canMove() {
//        return currentState.canMove(this);
//    }
//
//    /**
//     * Check if unit can attack based on current state.
//     * Replaces: !attackedThisTurn check
//     */
//    public boolean canAttack() {
//        return currentState.canAttack(this);
//    }

    /**
     * Check if unit can use abilities based on current state.
     */
    public boolean canUseAbility() {
        return currentState.canUseAbility(this);
    }

    /**
     *
     * Check if unit has actions left this turn.
     */
    public boolean hasActionsLeft(){
        return currentState.hasActionsLeft(this);
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

//    public void markMovedThisTurn() {
//        movedThisTurn = true;
//    }
//
//    public void markAttackedThisTurn() {
//        attackedThisTurn = true;
//    }
//
//    public void clearMovedThisTurn() {
//        movedThisTurn = false;
//    }
//
//    public void clearAttackedThisTurn() {
//        attackedThisTurn = false;
//    }
//
//    public boolean hasMovedThisTurn() {
//        return movedThisTurn;
//    }
//
//    public boolean hasAttackedThisTurn() {
//        return attackedThisTurn;
//    }

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
        decorators.removeIf(d -> decoratorClass.isInstance(d));
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
}