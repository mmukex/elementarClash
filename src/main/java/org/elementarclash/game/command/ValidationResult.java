package org.elementarclash.game.command;

/**
 * Immutable result of command validation.
 * Contains success flag and optional error message.
 * <p>
 * This is a value object that encapsulates validation results
 * from Command.validate() methods.
 *
 * @author mmukex
 */
public record ValidationResult(boolean isValid, String errorMessage) {

    public ValidationResult {
        if (!isValid && (errorMessage == null || errorMessage.isBlank())) {
            throw new IllegalArgumentException("Failure must include error message");
        }
    }

    public static ValidationResult success() {
        return new ValidationResult(true, null);
    }

    public static ValidationResult failure(String message) {
        return new ValidationResult(false, message);
    }
}
