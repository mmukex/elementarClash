package org.elementarclash.game;

import lombok.Getter;

/**
 * Manages turn and round counting for the game.
 */
@Getter
public class RoundManager {
    private static final int INITIAL_ROUND = 0;
    private static final int STARTING_ROUND = 1;

    /**
     * -- GETTER --
     * Returns the current turn number.
     */
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

}
