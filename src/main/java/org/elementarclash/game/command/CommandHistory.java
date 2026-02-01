package org.elementarclash.game.command;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Manages command history for undo/redo functionality.
 * Maintains separate stacks for executed commands and undone commands.
 * <p>
 * Design Pattern: Command (GoF #4) - History Management
 * Why: Enables undo/redo by tracking command execution order.
 * <p>
 * Scope: Per turn (cleared when turn ends)
 * <p>
 * Implementation:
 * - executedCommands: Stack of commands that have been executed (for undo)
 * - undoneCommands: Stack of commands that have been undone (for redo)
 * - New command execution clears redo stack (timeline branch)
 *
 * @author mmukex
 */
public class CommandHistory {

    private final Deque<Command> executedCommands;
    private final Deque<Command> undoneCommands;

    public CommandHistory() {
        this.executedCommands = new ArrayDeque<>();
        this.undoneCommands = new ArrayDeque<>();
    }

    public void push(Command command) {
        executedCommands.push(command);
        undoneCommands.clear();
    }

    public void pushUndone(Command command) {
        executedCommands.remove(command);
        undoneCommands.push(command);
    }

    public Command popForUndo() {
        if (executedCommands.isEmpty()) {
            return null;
        }
        Command command = executedCommands.pop();
        undoneCommands.push(command);
        return command;
    }

    public Command popForRedo() {
        if (undoneCommands.isEmpty()) {
            return null;
        }
        Command command = undoneCommands.pop();
        executedCommands.push(command);
        return command;
    }

    public void clear() {
        executedCommands.clear();
        undoneCommands.clear();
    }
}
