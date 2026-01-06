package org.elementarclash.game.command;

import org.elementarclash.game.Game;

/**
 * Executes and manages game commands with undo/redo capability.
 * Maintains command history and coordinates with CommandHistory for undo/redo operations.
 * <p>
 * Design Pattern: Command (GoF #4) - Invoker
 * Why: Centralizes command execution and history management.
 * Separates command invocation from command implementation.
 *
 * @author mmukex
 */
public class CommandExecutor {
    private final CommandHistory history;

    public CommandExecutor() {
        this.history = new CommandHistory();
    }

    public ValidationResult execute(Command command, Game game) {
        ValidationResult result = command.validate(game);
        if (!result.isValid()) {
            return result;
        }

        try {
            command.execute(game);
            history.push(command);
            return ValidationResult.success();
        } catch (Exception e) {
            return ValidationResult.failure("Execution failed: " + e.getMessage());
        }
    }

    public boolean undo(Game game) {
        Command command = history.popForUndo();
        if (command == null) {
            return false;
        }

        try {
            command.undo(game);
            return true;
        } catch (Exception e) {
            history.push(command);
            throw new IllegalStateException("Undo failed: " + e.getMessage(), e);
        }
    }

    public boolean redo(Game game) {
        Command command = history.popForRedo();
        if (command == null) {
            return false;
        }

        try {
            command.execute(game);
            return true;
        } catch (Exception e) {
            history.pushUndone(command);
            throw new IllegalStateException("Redo failed: " + e.getMessage(), e);
        }
    }

    public void clearHistory() {
        history.clear();
    }

    public boolean canUndo() {
        return history.canUndo();
    }

    public boolean canRedo() {
        return history.canRedo();
    }
}
