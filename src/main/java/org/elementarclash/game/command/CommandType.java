package org.elementarclash.game.command;

/**
 * Types of commands for action tracking and logging.
 * <p>
 * Used by Command.getType() to identify the command type
 * for debugging, logging, and analytics.
 */
public enum CommandType {
    MOVE,
    ATTACK
}
