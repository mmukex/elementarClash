package org.elementarclash.game.command;

import lombok.Getter;
import org.elementarclash.game.Game;
import org.elementarclash.units.Unit;
import org.elementarclash.util.Position;

/**
 * Command for moving a unit to a new position.
 * Validates movement using unit's MovementStrategy.
 * Stores previous position for undo.
 * <p>
 * Design Pattern: Command (GoF #4)
 * Why: Encapsulates move action with validation and undo capability.
 * <p>
 * Validation:
 * 1. Unit exists in game
 * 2. Unit is alive
 * 3. Unit hasn't moved this turn
 * 4. Movement is valid (delegates to Strategy)
 * <p>
 * Undo: Restores previous position and clears moved flag
 */
@Getter
public class MoveCommand implements Command {

    private final Unit actor;
    private final Position targetPosition;

    private Position previousPosition;
    private boolean wasExecuted;

    public MoveCommand(Unit unit, Position targetPosition) {
        this.actor = unit;
        this.targetPosition = targetPosition;
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

        if (actor.hasMovedThisTurn()) {
            return ValidationResult.failure(
                    String.format("%s has already moved this turn", actor.getName())
            );
        }

        if (!game.isValidMove(actor, targetPosition)) {
            return ValidationResult.failure(
                    String.format("Invalid move to %s (range/terrain/occupied)", targetPosition)
            );
        }

        return ValidationResult.success();
    }

    @Override
    public void execute(Game game) {
        this.previousPosition = actor.getPosition();
        game.moveUnitInternal(actor, targetPosition);
        actor.markMovedThisTurn();
        this.wasExecuted = true;
    }

    @Override
    public void undo(Game game) {
        if (!wasExecuted) {
            throw new IllegalStateException("Cannot undo command that wasn't executed");
        }

        game.moveUnitInternal(actor, previousPosition);
        actor.clearMovedThisTurn();
    }

    @Override
    public CommandType getType() {
        return CommandType.MOVE;
    }

    @Override
    public String toString() {
        return String.format("MoveCommand[%s: %s → %s]",
                actor.getName(), previousPosition, targetPosition);
    }
}
