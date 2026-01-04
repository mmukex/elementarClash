package org.elementarclash.units;

import lombok.Getter;
import lombok.Setter;
import org.elementarclash.battlefield.visitor.TerrainEffectResult;
import org.elementarclash.battlefield.visitor.TerrainVisitor;
import org.elementarclash.faction.Faction;
import org.elementarclash.units.state.IdleState;
import org.elementarclash.units.state.UnitState;
import org.elementarclash.units.strategy.attack.AttackStrategy;
import org.elementarclash.units.strategy.attack.MeleeAttackStrategy;
import org.elementarclash.units.strategy.movement.GroundMovementStrategy;
import org.elementarclash.units.strategy.movement.MovementStrategy;
import org.elementarclash.util.Position;

import java.util.HashMap;
import java.util.Map;

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
    private boolean movedThisTurn;
    private boolean attackedThisTurn;

    @Setter
    private MovementStrategy movementStrategy;
    private AttackStrategy attackStrategy;
    private final Map<Class<?>, Integer> abilityCooldowns = new HashMap<>();

    // Getter / Setter über Lombok?
    private UnitState currentState;

    protected Unit(String id, String name, Faction faction, UnitType type, UnitStats stats) {
        this.id = id;
        this.name = name;
        this.faction = faction;
        this.type = type;
        this.baseStats = stats;
        this.currentHealth = stats.maxHealth();
        this.movedThisTurn = false;
        this.attackedThisTurn = false;
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

    public boolean isAbilityOnCooldown(Class<?> abilityClass) {
        return abilityCooldowns.getOrDefault(abilityClass, 0) > 0;
    }

    public int getAbilityCooldown(Class<?> abilityClass) {
        return abilityCooldowns.getOrDefault(abilityClass, 0);
    }

    public void startAbilityCooldown(Class<?> abilityClass, int cooldown) {
        if (cooldown > 0) {
            abilityCooldowns.put(abilityClass, cooldown);
        }
    }

    public void clearAbilityCooldown(Class<?> abilityClass) {
        abilityCooldowns.remove(abilityClass);
    }

    private void tickAbilityCooldowns() {
        abilityCooldowns.replaceAll((k, v) -> Math.max(0, v - 1));
    }

    public void resetTurn() {
        tickAbilityCooldowns();
        movedThisTurn = false;
        attackedThisTurn = false;

        //State Pattern
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
    public boolean canMove() {
        return currentState.canMove(this);
    }

    /**
     * Check if unit can attack based on current state.
     * Replaces: !attackedThisTurn check
     */
    public boolean canAttack() {
        return currentState.canAttack(this);
    }

    /**
     * Check if unit can use abilities based on current state.
     */
    public boolean canUseAbility() {
        return currentState.canUseAbility(this);
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
        return canMove() || canAttack();
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