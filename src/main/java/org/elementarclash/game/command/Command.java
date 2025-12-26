package org.elementarclash.game.command;

import org.elementarclash.game.Game;
import org.elementarclash.units.Unit;

/**
 * Command Pattern interface for game actions.
 * Encapsulates validation, execution, and undo logic for unit actions.
 * <p>
 * Design Pattern: Command (GoF #4)
 * Why: Decouples action invocation from execution, enables undo/redo.
 * <p>
 * Implementations:
 * - MoveCommand: Unit movement with position rollback
 * - AttackCommand: Unit attack with health rollback
 * - UseAbilityCommand: Special abilities (slow, resurrect, pierce)
 */
public interface Command {

    /**
     * Validates if this command can be executed in the current game state.
     * Checks preconditions like unit existence, alive status, range, cooldowns, etc.
     *
     * @param game current game state
     * @return validation result with success flag and optional error message
     */
    ValidationResult validate(Game game);

    /**
     * Executes this command, modifying game state.
     * Should only be called after successful validation.
     * Stores state needed for undo().
     *
     * @param game Current game state
     * @throws IllegalStateException if validation fails
     */
    void execute(Game game);

    /**
     * Reverses the effects of execute().
     * Restores unit positions, health, and action flags.
     *
     * @param game Current game state
     */
    void undo(Game game);

    /**
     * Returns the unit performing this action.
     * Used for action limit tracking.
     *
     * @return the unit actor
     */
    Unit getActor();

    /**
     * Returns the command type for logging/debugging.
     *
     * @return the command type
     */
    CommandType getType();

    /**
     * Common validation: Check if actor exists in game.
     * Reduces code duplication across command implementations.
     *
     * @param game  Current game state
     * @param actor Unit to validate
     * @return ValidationResult indicating if actor exists in game
     */
    default ValidationResult validateActorExists(Game game, Unit actor) {
        if (!game.getUnits().contains(actor)) {
            return ValidationResult.failure("Unit not in game");
        }
        return ValidationResult.success();
    }

    /**
     * Common validation: Check if actor is alive.
     * Reduces code duplication across command implementations.
     *
     * @param actor Unit to validate
     * @return ValidationResult indicating if actor is alive
     */
    default ValidationResult validateActorAlive(Unit actor) {
        if (!actor.isAlive()) {
            return ValidationResult.failure("Dead units cannot act");
        }
        return ValidationResult.success();
    }

    /**
     * Common validation: Check if target exists in game and is alive.
     * Reduces code duplication in attack/ability commands.
     *
     * @param game   Current game state
     * @param target Target unit to validate
     * @return ValidationResult indicating if target is valid
     */
    default ValidationResult validateTargetExists(Game game, Unit target) {
        if (!game.getUnits().contains(target)) {
            return ValidationResult.failure("Target not in game");
        }
        if (!target.isAlive()) {
            return ValidationResult.failure("Cannot target dead units");
        }
        return ValidationResult.success();
    }
}
