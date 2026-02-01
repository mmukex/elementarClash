package org.elementarclash.ui;

import org.elementarclash.game.Game;
import org.elementarclash.game.command.AttackCommand;
import org.elementarclash.game.command.MoveCommand;
import org.elementarclash.game.command.ValidationResult;
import org.elementarclash.game.event.observer.EventLogObserver;
import org.elementarclash.game.phase.GameOverPhase;
import org.elementarclash.units.Faction;

/**
 * Controls the main game loop.
 * Single Responsibility: Game flow orchestration.
 */
public class GameController {
    private final Game game;
    private final ConsoleUI ui;
    private final CommandParser parser;
    private final GameRenderer renderer;
    private final EventLogObserver eventLog;

    public GameController(Game game) {
        this.game = game;
        this.ui = new ConsoleUI();
        this.parser = new CommandParser(ui);
        this.renderer = new ConsoleGameRenderer();
        this.eventLog = new EventLogObserver();

        // register Observers
        game.addObserver((ConsoleGameRenderer) renderer);
        game.addObserver(eventLog);
    }

    public void start() {
        game.startGame();

        while (!(game.getCurrentPhase() instanceof GameOverPhase)) {
            displayGameState();
            processTurn();
        }

        displayWinner();
        ui.close();
    }

    private void displayGameState() {
        ui.display(System.lineSeparator() + "=".repeat(60));
        ui.display(renderer.render(game));
        ui.display("=".repeat(60));
    }

    private void processTurn() {
        boolean turnEnded = false;

        while (!turnEnded && !(game.getCurrentPhase() instanceof GameOverPhase)) {
            String action = ui.promptAction();
            turnEnded = handleAction(action);
        }

        game.nextTurn();
    }

    private boolean handleAction(String action) {
        return switch (action) {
            case "B" -> executeMove();
            case "A" -> executeAttack();
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

        if (ui.promptViewLog("\nEvent-Log anzeigen? (j/n): ").equalsIgnoreCase("j")) {
            eventLog.printLog();
        }
    }
}
