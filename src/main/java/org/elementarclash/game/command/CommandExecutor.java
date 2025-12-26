package org.elementarclash.game.command;

import org.elementarclash.game.Game;

/**
 * Executes and manages game commands with undo/redo capability.
 */
public class CommandExecutor {
    private final CommandHistory history;

    public CommandExecutor() {
        this.history = new CommandHistory();
    }

    /**
     * Executes a command if valid and adds it to history.
     */
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

    /**
     * Undoes the last command.
     */
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

    /**
     * Redoes the last undone command.
     */
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

    /**
     * Clears command history (called at turn end).
     */
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
