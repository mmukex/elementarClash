package org.elementarclash.game.ability;

import org.elementarclash.game.Game;
import org.elementarclash.game.command.ValidationResult;
import org.elementarclash.units.Unit;

/**
 * Interface for special unit abilities.
 * Abilities are actions beyond basic move/attack, like healing, area effects, or status changes.
 * <p>
 * Design Pattern: Strategy (for ability execution)
 * Why: Different units have different passive abilities with varying effects.
 * This interface allows abilities to be implemented independently and attached to units.
 * <p>
 * Note: ElementarClash focuses on passive unit abilities and terrain interactions.
 * Active abilities have been removed to reduce complexity.
 * <p>
 * Passive abilities include:
 * - Phoenix resurrection (automatic on death)
 * - Storm Caller chain lightning (built into attack)
 * - Terrain-based bonuses (via Visitor Pattern)
 * <p>
 * Generic Parameter:
 * - T: Type of undo state (ability-specific)
 *
 * @param <T> type of undo state for this ability
 */
public interface Ability<T> {

    /**
     * Returns the ability's name (e.g., "Frost Slow", "Phoenix Rebirth").
     *
     * @return ability name
     */
    String getName();

    /**
     * Returns the ability's description (e.g., "Reduces target movement by 50% for 2 turns").
     *
     * @return ability description
     */
    String getDescription();

    /**
     * Validates whether this ability can be used.
     * Checks cooldowns, resource costs, valid targets, range, etc.
     *
     * @param game    game instance for state access
     * @param actor   unit using the ability
     * @param targets targets of the ability (may be empty for self-targeting abilities)
     * @return validation result indicating success or failure with reason
     */
    ValidationResult validate(Game game, Unit actor, Object[] targets);

    /**
     * Executes the ability and returns state needed for undo.
     * Should not validate - validation must be done separately via validate().
     *
     * @param game    game instance for state modification
     * @param actor   unit using the ability
     * @param targets targets of the ability
     * @return undo state (ability-specific), or null if no undo needed
     */
    T execute(Game game, Unit actor, Object[] targets);

    /**
     * Undoes the ability using stored state.
     *
     * @param game      game instance for state restoration
     * @param actor     unit that used the ability
     * @param undoState state returned from execute()
     */
    void undo(Game game, Unit actor, T undoState);

    /**
     * Returns whether this ability consumes the unit's action for the turn.
     * Default: true (most abilities consume the action).
     *
     * @return true if using this ability marks the unit as acted
     */
    default boolean consumesAction() {
        return true;
    }

    /**
     * Returns the cooldown of this ability in turns.
     * Default: 0 (no cooldown, can be used every turn if action available).
     *
     * @return cooldown in turns
     */
    default int getCooldown() {
        return 0;
    }
}
