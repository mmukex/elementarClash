package org.elementarclash.ui;

import org.elementarclash.faction.Faction;
import org.elementarclash.game.Game;
import org.elementarclash.game.Game.GameStatus;
import org.elementarclash.game.command.AttackCommand;
import org.elementarclash.game.command.MoveCommand;
import org.elementarclash.game.command.UseAbilityCommand;
import org.elementarclash.game.command.ValidationResult;

/**
 * Controls the main game loop.
 * Single Responsibility: Game flow orchestration.
 */
public class GameController {
    private final Game game;
    private final ConsoleUI ui;
    private final CommandParser parser;
    private final GameRenderer renderer;

    public GameController(Game game) {
        this.game = game;
        this.ui = new ConsoleUI();
        this.parser = new CommandParser(ui);
        this.renderer = new ConsoleGameRenderer();
    }

    public void start() {
        game.startGame();

        while (game.getStatus() == GameStatus.IN_PROGRESS) {
            displayGameState();
            processTurn();
        }

        displayWinner();
        ui.close();
    }

    private void displayGameState() {
        ui.display(System.lineSeparator() + "=".repeat(60));
        ui.display(renderer.render(game));
        ui.display("Runde: " + game.getTurnNumber() + " | Aktive Fraktion: " + game.getActiveFaction());
        ui.display("=".repeat(60));
    }

    private void processTurn() {
        boolean turnEnded = false;

        while (!turnEnded && game.getStatus() == GameStatus.IN_PROGRESS) {
            String action = ui.promptAction();
            turnEnded = handleAction(action);
        }

        game.nextTurn();
    }

    private boolean handleAction(String action) {
        return switch (action) {
            case "B" -> executeMove();
            case "A" -> executeAttack();
            case "F" -> executeAbility();
            case "U" -> executeUndo();
            case "R" -> executeRedo();
            case "Z" -> true;
            case "Q" -> executeQuit();
            default -> {
                ui.showError("Ungültige Eingabe: " + action);
                yield false;
            }
        };
    }

    private boolean executeMove() {
        MoveCommand command = parser.parseMove(game);
        if (command == null) {
            return false;
        }

        ValidationResult result = game.executeCommand(command);
        if (result.isValid()) {
            ui.showSuccess("Einheit bewegt");
            displayGameState();
        } else {
            ui.showError(result.errorMessage());
        }

        return false;
    }

    private boolean executeAttack() {
        AttackCommand command = parser.parseAttack(game);
        if (command == null) {
            return false;
        }

        ValidationResult result = game.executeCommand(command);
        if (result.isValid()) {
            ui.showSuccess("Angriff ausgeführt");
            displayGameState();
        } else {
            ui.showError(result.errorMessage());
        }

        return false;
    }

    private boolean executeAbility() {
        UseAbilityCommand command = parser.parseAbility(game);
        if (command == null) {
            return false;
        }

        ValidationResult result = game.executeCommand(command);
        if (result.isValid()) {
            ui.showSuccess("Fähigkeit eingesetzt");
            displayGameState();
        } else {
            ui.showError(result.errorMessage());
        }

        return false;
    }

    private boolean executeUndo() {
        if (game.undoLastCommand()) {
            ui.showSuccess("Aktion rückgängig gemacht");
            displayGameState();
        } else {
            ui.showError("Nichts zum Rückgängigmachen");
        }
        return false;
    }

    private boolean executeRedo() {
        if (game.redoLastCommand()) {
            ui.showSuccess("Aktion wiederholt");
            displayGameState();
        } else {
            ui.showError("Nichts zum Wiederholen");
        }
        return false;
    }

    private boolean executeQuit() {
        ui.display(System.lineSeparator() + "Spiel wird beendet...");
        System.exit(0);
        return true;
    }

    private void displayWinner() {
        Faction winner = game.getWinner();
        if (winner != null) {
            ui.display(System.lineSeparator() + winner + " hat gewonnen!");
        } else {
            ui.display(System.lineSeparator() + "Unentschieden!");
        }
    }
}
