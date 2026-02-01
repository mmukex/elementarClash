package org.elementarclash.game.event;

/**
 * Observer Pattern (GoF #9)
 *
 * Observer for game events (unit movement, attacks, deaths, terrain changes).
 *
 * Why Observer Pattern?
 * - Decouples game logic from UI updates
 * - Multiple observers can listen to same events (UI + logger + AI)
 * - Easy to add new observers without modifying Game class
 *
 * @author @crstmkt
 */
public interface GameObserver {

    /**
     * Called when a game event occurs.
     *
     * @param event the event that occurred
     */
    void onEvent(GameEvent event);
}