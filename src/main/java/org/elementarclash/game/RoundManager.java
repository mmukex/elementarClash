package org.elementarclash.game;

/**
 * Manages turn and round counting for the game.
 *
 * TODO: crstmkt - JavaDoc
 */
public class RoundManager {
    private static final int INITIAL_ROUND = 0;
    private static final int STARTING_ROUND = 1;

    private int roundNumber;

    public RoundManager() {
        this.roundNumber = INITIAL_ROUND;
    }

    /**
     * Starts the game by setting turn number to 1.
     */
    public void startGame() {
        this.roundNumber = STARTING_ROUND;
    }

    /**
     * Increments the turn number (called when round cycles).
     */
    public void incrementRound() {
        this.roundNumber++;
    }

    /**
     * Returns the current turn number.
     */
    public int getRoundNumber() {
        return roundNumber;
    }
}
