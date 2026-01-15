package org.elementarclash.game.phase;

import lombok.Getter;
import org.elementarclash.game.Game;
import org.elementarclash.game.command.Command;
import org.elementarclash.units.Faction;
import org.elementarclash.game.command.ValidationResult;

/**
 * Player turn phase: Commands are allowed.
 * Max 2 actions per unit (move + attack/ability).
 */
@Getter
public class PlayerTurnPhase implements GamePhaseState {

    private final Faction activeFaction;
    // private int actionsThisTurn = 0;
    // private static final int MAX_ACTIONS_PER_UNIT = 2;

    public PlayerTurnPhase(Faction faction) {
        this.activeFaction = faction;
    }

    @Override
    public boolean canExecuteCommand(Game game, Command command) {

        // Check if command actor belongs to active faction
        // Kann das vereinfacht werden zu return command.validate(game).isValid()?
        ValidationResult result = command.validate(game);
        if (!result.isValid()) {
            return false;
        }
        return true;

//
//        // Check action limit (simplified - could be per-unit)
//        return actionsThisTurn < MAX_ACTIONS_PER_UNIT;
    }

    @Override
    public void onEnter(Game game) {
        // Reset all units of active faction
        game.getUnitsOfFaction(activeFaction).forEach(unit -> {
            unit.resetTurn();
        });
    }

    @Override
    public void onExit(Game game) {
        // Could log turn end, save state, etc.
    }

    @Override
    public GamePhaseState transitionToPlayerTurn(Game game, Faction faction) {
        return new PlayerTurnPhase(faction);
    }

    @Override
    public GamePhaseState transitionToEventPhase(Game game) {
        return EventPhase.getInstance();
    }

    @Override
    public GamePhaseState transitionToGameOver(Game game, Faction winner) {
        return new GameOverPhase(winner);
    }

    @Override
    public String getPhaseName() {
        return "PlayerTurn (" + activeFaction.name() + ")";
    }

    //    public void incrementActionCount() {
//        actionsThisTurn++;
//    }
//
//    public void decrementActionCount(){
//        actionsThisTurn--;
//    }
}