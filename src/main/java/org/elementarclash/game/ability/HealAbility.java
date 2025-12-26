package org.elementarclash.game.ability;

import org.elementarclash.game.Game;
import org.elementarclash.game.command.ValidationResult;
import org.elementarclash.units.Unit;

/**
 * Self-healing ability.
 * Restores a fixed amount of health to the user.
 * Cannot be used at full health.
 * <p>
 * Design Pattern: Strategy (implements Ability interface)
 * Why: Demonstrates ability pattern with simple, testable implementation.
 * <p>
 * Example Usage:
 * - Water faction healers
 * - Units with regeneration abilities
 * - Special hero units
 * <p>
 * Undo State:
 * - Stores actual amount healed (may be less than healAmount if near max health)
 */
public class HealAbility implements Ability<HealAbility.HealUndoState> {

    private final int healAmount;

    public HealAbility(int healAmount) {
        if (healAmount <= 0) {
            throw new IllegalArgumentException("Heal amount must be positive");
        }
        this.healAmount = healAmount;
    }

    @Override
    public String getName() {
        return "Heal";
    }

    @Override
    public String getDescription() {
        return String.format("Restores %d health", healAmount);
    }

    @Override
    public ValidationResult validate(Game game, Unit actor, Object[] targets) {
        // Check actor is wounded
        if (actor.getCurrentHealth() >= actor.getBaseStats().maxHealth()) {
            return ValidationResult.failure(
                    String.format("%s is already at full health", actor.getName())
            );
        }

        return ValidationResult.success();
    }

    @Override
    public HealUndoState execute(Game game, Unit actor, Object[] targets) {
        int previousHealth = actor.getCurrentHealth();
        actor.heal(healAmount);
        int actualHealed = actor.getCurrentHealth() - previousHealth;

        return new HealUndoState(actualHealed);
    }

    @Override
    public void undo(Game game, Unit actor, HealUndoState undoState) {
        if (undoState == null) {
            throw new IllegalStateException("Cannot undo heal without state");
        }

        // Remove the health that was healed
        actor.takeDamage(undoState.amountHealed);
    }

    @Override
    public boolean consumesAction() {
        return true; // Healing consumes the unit's action
    }

    public record HealUndoState(int amountHealed) {
    }
}
