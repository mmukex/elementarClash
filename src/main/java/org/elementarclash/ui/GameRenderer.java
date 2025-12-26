package org.elementarclash.ui;

import org.elementarclash.game.Game;

/**
 * Renders game state to various output formats.
 * Separates presentation logic from game logic.
 */
public interface GameRenderer {
    /**
     * Renders the game state.
     *
     * @param game game to render
     * @return rendered output
     */
    String render(Game game);
}
