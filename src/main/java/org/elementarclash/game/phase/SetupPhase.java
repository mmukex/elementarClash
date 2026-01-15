package org.elementarclash.game.phase;

import org.elementarclash.game.Game;
import org.elementarclash.game.command.Command;
import org.elementarclash.units.Faction;

/**
 * Setup phase: Game is being constructed via GameBuilder.
 * No commands allowed.
 */
public class SetupPhase implements GamePhaseState {

    private static final SetupPhase INSTANCE = new SetupPhase();

    public static SetupPhase getInstance() {
        return INSTANCE;
    }

    private SetupPhase() {}

    @Override
    public boolean canExecuteCommand(Game game, Command command) {
        return false; // No commands during setup
    }

    @Override
    public void onEnter(Game game) {
        // No special logic
    }

    @Override
    public void onExit(Game game) {
        // Validate game is ready to start
    }

    @Override
    public GamePhaseState transitionToPlayerTurn(Game game, Faction faction) {
        return new PlayerTurnPhase(faction);
    }

    @Override
    public GamePhaseState transitionToEventPhase(Game game) {
        throw new IllegalStateException("Cannot transition from Setup to EventPhase");
    }

    @Override
    public GamePhaseState transitionToGameOver(Game game, Faction winner) {
        return new GameOverPhase(winner);
    }

    @Override
    public String getPhaseName() {
        return "Setup";
    }
}