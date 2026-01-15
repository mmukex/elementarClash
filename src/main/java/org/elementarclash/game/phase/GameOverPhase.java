package org.elementarclash.game.phase;

import lombok.Getter;
import org.elementarclash.game.Game;
import org.elementarclash.game.command.Command;
import org.elementarclash.units.Faction;

/**
 * Game over phase: Game has ended.
 * No commands allowed.
 */
@Getter
public class GameOverPhase implements GamePhaseState {

    private final Faction winner;

    public GameOverPhase(Faction winner) {
        this.winner = winner;
    }

    @Override
    public boolean canExecuteCommand(Game game, Command command) {
        return false; // No commands after game over
    }

    @Override
    public void onEnter(Game game) {
        System.out.println("\n═══════════════════════════════════════");
        System.out.println("       GAME OVER - " + winner.name() + " WINS!");
        System.out.println("═══════════════════════════════════════\n");
    }

    @Override
    public void onExit(Game game) {
        // No exit logic (terminal state)
    }

    @Override
    public GamePhaseState transitionToPlayerTurn(Game game, Faction faction) {
        return this; // Cannot transition from game over
    }

    @Override
    public GamePhaseState transitionToEventPhase(Game game) {
        return this;
    }

    @Override
    public GamePhaseState transitionToGameOver(Game game, Faction winner) {
        return this;
    }

    @Override
    public String getPhaseName() {
        return "GameOver (Winner: " + winner.name() + ")";
    }

}