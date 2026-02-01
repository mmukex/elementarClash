package org.elementarclash.game.phase;

import org.elementarclash.game.Game;
import org.elementarclash.game.command.Command;
import org.elementarclash.units.Faction;

/**
 * State Pattern (GoF #8) - Game Phase State Machine
 * <p>
 * Manages game flow: Setup → PlayerTurn → EventPhase → (repeat) → GameOver
 * <p>
 * Why State Pattern?
 * - Clear separation of game phases
 * - Each phase has different allowed actions
 * - Clean state transitions (no complex if-else chains)
 *
 * @author @crstmkt
 */
public interface GamePhaseState {

    /**
     * Can commands be executed in this phase?
     */
    boolean canExecuteCommand(Game game, Command command);

    /**
     * Enter this phase (called on transition).
     */
    void onEnter(Game game);

    /**
     * Exit this phase (called before transition).
     */
    void onExit(Game game);

    /**
     * Transition to PlayerTurn phase.
     */
    GamePhaseState transitionToPlayerTurn(Game game, Faction faction);

    /**
     * Transition to EventPhase.
     */
    GamePhaseState transitionToEventPhase(Game game);

    /**
     * Transition to GameOver phase.
     */
    GamePhaseState transitionToGameOver(Game game, Faction winner);

    /**
     * Get phase name for debugging/UI.
     */
    String getPhaseName();
}