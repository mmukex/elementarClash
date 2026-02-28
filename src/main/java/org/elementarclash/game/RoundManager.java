package org.elementarclash.game;

import lombok.Getter;

/**
 * Manages turn and round counting for the game.
 *
 * @author mmukex
 */
@Getter
public class RoundManager {
    private static final int INITIAL_ROUND = 0;
    private static final int STARTING_ROUND = 1;

    private int roundNumber;

    public RoundManager() {
        this.roundNumber = INITIAL_ROUND;
    }

    public void startGame() {
        this.roundNumber = STARTING_ROUND;
    }

    public void incrementRound() {
        this.roundNumber++;
    }
}
