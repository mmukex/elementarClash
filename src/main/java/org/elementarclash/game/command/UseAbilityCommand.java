package org.elementarclash.game.command;

import lombok.Getter;
import org.elementarclash.game.Game;
import org.elementarclash.game.ability.Ability;
import org.elementarclash.units.Unit;

/**
 * Command for using a special unit ability.
 * Validates and executes abilities, storing ability-specific undo state.
 * <p>
 * Design Pattern: Command (GoF #4)
 * Why: Encapsulates ability usage with validation and undo capability.
 * Abilities vary widely in their effects, so this command delegates
 * most logic to the Ability interface.
 * <p>
 * Validation:
 * 1. Unit exists in game
 * 2. Unit is alive
 * 3. Delegates to ability.validate() for ability-specific checks
 * (cooldown, targets, resources, etc.)
 * <p>
 * Execution:
 * - Delegates to ability.execute()
 * - Captures ability-specific undo state
 * - Optionally marks unit as acted (if ability.consumesAction())
 * <p>
 * Undo:
 * - Delegates to ability.undo() with captured state
 * - Restores action flag if ability consumed action
 *
 * @param <T> type of undo state for the ability
 */
@Getter
public class UseAbilityCommand<T> implements Command {

    private final Unit actor;
    private final Ability<T> ability;
    private final Object[] targets;

    private T undoState;
    private boolean actionWasConsumed;
    private boolean wasExecuted;

    public UseAbilityCommand(Unit actor, Ability<T> ability, Object... targets) {
        this.actor = actor;
        this.ability = ability;
        this.targets = targets;
    }

    @Override
    public ValidationResult validate(Game game) {
        ValidationResult actorCheck = validateActorExists(game, actor);
        if (!actorCheck.isValid()) {
            return actorCheck;
        }

        actorCheck = validateActorAlive(actor);
        if (!actorCheck.isValid()) {
            return actorCheck;
        }

        if (actor.isAbilityOnCooldown(ability.getClass())) {
            int remainingCooldown = actor.getAbilityCooldown(ability.getClass());
            return ValidationResult.failure(
                String.format("Fähigkeit noch %d Runden auf Abklingzeit", remainingCooldown)
            );
        }

        return ability.validate(game, actor, targets);
    }

    @Override
    public void execute(Game game) {
        this.undoState = ability.execute(game, actor, targets);

        actor.startAbilityCooldown(ability.getClass(), ability.getCooldown());

        this.actionWasConsumed = ability.consumesAction();
        if (actionWasConsumed) {
            actor.markAttackedThisTurn();
        }

        this.wasExecuted = true;
    }

    @Override
    public void undo(Game game) {
        if (!wasExecuted) {
            throw new IllegalStateException("Cannot undo command that wasn't executed");
        }

        ability.undo(game, actor, undoState);

        actor.clearAbilityCooldown(ability.getClass());

        if (actionWasConsumed) {
            actor.clearAttackedThisTurn();
        }
    }

    @Override
    public CommandType getType() {
        return CommandType.USE_ABILITY;
    }

    @Override
    public String toString() {
        return String.format("UseAbilityCommand[%s uses %s]",
                actor.getName(), ability.getName());
    }
}
