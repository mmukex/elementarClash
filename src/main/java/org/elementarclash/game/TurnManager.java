package org.elementarclash.game;

/**
 * Manages turn and round counting for the game.
 *
 * TODO: crstmkt - State Pattern - Add turn phase management - DONE
 */
public class TurnManager {
    private static final int INITIAL_ROUND = 0;
    private static final int STARTING_ROUND = 1;

    private int turnNumber;

    public TurnManager() {
        this.turnNumber = INITIAL_ROUND;
    }

    /**
     * Starts the game by setting turn number to 1.
     */
    public void startGame() {
        this.turnNumber = STARTING_ROUND;
    }

    /**
     * Increments the turn number (called when round cycles).
     */
    public void incrementTurn() {
        this.turnNumber++;
    }

    /**
     * Returns the current turn number.
     */
    public int getTurnNumber() {
        return turnNumber;
    }
}
