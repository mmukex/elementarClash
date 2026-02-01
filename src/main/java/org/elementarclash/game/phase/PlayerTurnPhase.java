package org.elementarclash.game.phase;

import org.elementarclash.game.Game;
import org.elementarclash.game.command.Command;
import org.elementarclash.game.command.ValidationResult;
import org.elementarclash.units.Faction;
import org.elementarclash.units.Unit;
import org.elementarclash.units.bonus.BuffDebuffManager;

/**
 * Player turn phase: Commands are allowed.
 * Max 2 actions per unit (move + attack/ability).
 */
public record PlayerTurnPhase(Faction activeFaction) implements GamePhaseState {

    @Override
    public boolean canExecuteCommand(Game game, Command command) {

        // Check if command actor belongs to active faction
        ValidationResult result = command.validate(game);
        return result.isValid();

    }

    @Override
    public void onEnter(Game game) {
        // Apply random (De-)Buff as decorator by chance
        BuffDebuffManager buffDebuffManager = new BuffDebuffManager();
        buffDebuffManager.tryApplyRandomEffect(game, game.getRoundNumber());

        // Reset all units of active faction
        game.getUnitsOfFaction(activeFaction).forEach(Unit::resetTurn);
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

}