package org.elementarclash;

import org.elementarclash.game.Game;
import org.elementarclash.game.RandomGameConfigurer;
import org.elementarclash.ui.GameController;

/**
 * Main entry point for ElementarClash game.
 * Creates a random game setup and starts the interactive game loop.
 */
public class Main {

    public static void main(String[] args) {
        Game game = new RandomGameConfigurer().createRandomGame();
        new GameController(game).start();
    }
}
