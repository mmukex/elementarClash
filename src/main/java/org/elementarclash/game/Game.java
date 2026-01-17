package org.elementarclash.game;

import lombok.Getter;
import org.elementarclash.battlefield.Battlefield;
import org.elementarclash.battlefield.Terrain;
import org.elementarclash.battlefield.terraineffect.TerrainEffectResult;
import org.elementarclash.battlefield.terraineffect.TerrainVisitor;
import org.elementarclash.battlefield.terraineffect.TerrainVisitorFactory;
import org.elementarclash.units.Faction;
import org.elementarclash.game.command.Command;
import org.elementarclash.game.command.CommandExecutor;
import org.elementarclash.game.command.ValidationResult;
import org.elementarclash.game.phase.GameOverPhase;
import org.elementarclash.game.phase.GamePhaseState;
import org.elementarclash.game.phase.SetupPhase;
import org.elementarclash.game.phase.PlayerTurnPhase;
import org.elementarclash.ui.ConsoleGameRenderer;
import org.elementarclash.ui.GameRenderer;
import org.elementarclash.units.Unit;
import org.elementarclash.units.bonus.SynergyBonus;
import org.elementarclash.units.bonus.TerrainBonus;
import org.elementarclash.util.Position;
import org.elementarclash.game.event.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Central game manager for ElementarClash.
 * Manages units, game state, turn-based mechanics, and command execution.
 * Coordinates interaction between units and battlefield.
 * <p>
 * Design Pattern: Builder (GoF #2) - see GameBuilder for construction
 * Why: Complex construction with terrain distribution, faction setup, and unit placement.
 * Package-private constructor enforces use of GameBuilder.
 * <p>
 * Design Pattern: Command (GoF #4) - executeCommand() as primary action interface
 * Why: All player actions (move, attack, abilities) are executed as Commands with validation and undo/redo.
 * CommandHistory maintains per-turn action history for rollback capability.
 * <p>
 * Responsibilities:
 * - Unit management (add, remove, query)
 * - Command execution and validation (via Strategy Pattern)
 * - Turn-based game flow (rounds, faction turns, victory conditions)
 * - Game state tracking (SETUP → IN_PROGRESS → GAME_OVER)
 */
@Getter
public class Game {

    private static final int SINGLE_FACTION_REMAINING = 1;

    private final Battlefield battlefield;
    private final List<Unit> units;
    private final Map<Position, Unit> positionToUnit;
    private final CommandExecutor commandExecutor;
    private final TurnManager turnManager;
    private Faction activeFaction;
    private GamePhaseState currentPhase;
    private final List<GameObserver> observers = new ArrayList<>();

    Game(Battlefield battlefield) {
        this.battlefield = battlefield;
        this.units = new ArrayList<>();
        this.positionToUnit = new HashMap<>();
        this.activeFaction = null;
        this.currentPhase = SetupPhase.getInstance();
        this.commandExecutor = new CommandExecutor();
        this.turnManager = new TurnManager();
    }

    private void transitionToPhase(GamePhaseState newPhase) {
        currentPhase.onExit(this);
        this.currentPhase = newPhase;
        newPhase.onEnter(this);
    }

    /**
     * Start the game (Setup → PlayerTurn).
     */
    public void startGame() {
        if (!(currentPhase instanceof SetupPhase)) {
            throw new IllegalStateException("Can only start game from Setup phase");
        }

        Faction firstFaction = determineFirstFaction();
        transitionToPhase(currentPhase.transitionToPlayerTurn(this, firstFaction));
        this.activeFaction = firstFaction;
        this.turnManager.startGame();
        notifyObservers(new GameStartedEvent());
    }

    /**
     * End current player's turn (PlayerTurn → EventPhase → next PlayerTurn).
     */
    public void endTurn() {
        if (!(currentPhase instanceof PlayerTurnPhase playerTurn)) {
            throw new IllegalStateException("Can only end turn during PlayerTurn phase");
        }

        // Check victory condition
        if (checkVictoryCondition()) {
            return; // Game is over
        }

        // Notify: current factions turn is ending
        Faction endingFaction = activeFaction;
        notifyObservers(new TurnEndedEvent(endingFaction));

        // PlayerTurn → EventPhase
        transitionToPhase(currentPhase.transitionToEventPhase(this));

        // EventPhase → next PlayerTurn
        Faction nextFaction = getNextFaction();
        transitionToPhase(currentPhase.transitionToPlayerTurn(this, nextFaction));
        this.activeFaction = nextFaction;

        // notify new factions turn is starting
        notifyObservers(new TurnStartedEvent(nextFaction));
    }

    /**
     * Check victory condition and transition to GameOver if met.
     */
    private boolean checkVictoryCondition() {
        Map<Faction, Long> aliveCounts = units.stream()
                .filter(Unit::isAlive)
                .collect(Collectors.groupingBy(Unit::getFaction, Collectors.counting()));

        if (aliveCounts.size() == 1) {
            Faction winner = aliveCounts.keySet().iterator().next();
            transitionToPhase(currentPhase.transitionToGameOver(this, winner));
            notifyObservers(new GameOverEvent(winner));
            return true;
        }
        return false;
    }

    public boolean isPositionOccupied(Position position) {
        return positionToUnit.containsKey(position);
    }

    public void addUnit(Unit unit, Position position) {
        if (isPositionOccupied(position)) {
            return;
        }

        units.add(unit);
        positionToUnit.put(position, unit);
        unit.setPosition(position);
    }

    public void removeUnit(Unit unit) {
        units.remove(unit);
        positionToUnit.remove(unit.getPosition());
    }

    public void moveUnitInternal(Unit unit, Position newPosition) {
        if (!units.contains(unit) || isPositionOccupied(newPosition)) {
            return;
        }
        Position oldPosition = unit.getPosition();

        positionToUnit.remove(unit.getPosition());
        positionToUnit.put(newPosition, unit);
        unit.setPosition(newPosition);

        applyTerrainTransformation(unit);
        notifyObservers(new UnitMovedEvent(unit, oldPosition, newPosition));
    }

    private void applyTerrainTransformation(Unit unit) {
        Terrain terrain = getTerrainAt(unit.getPosition());
        TerrainVisitor visitor = TerrainVisitorFactory.getVisitor(terrain);
        TerrainEffectResult effect = unit.accept(visitor);

        // Listeners: TerrainVisualRenderer, UnitStatRecalculator
        if (effect.terrainChange() != null) {
            battlefield.setTerrainAt(unit.getPosition(), effect.terrainChange(), this);
        }
        // Apply terrain bonus as Decorator
        // Remove old terrain bonus, add new one
        unit.removeDecoratorsOfType(TerrainBonus.class);
        if (effect.attackBonus() != 0 || effect.defenseBonus() != 0) {
            unit.addDecorator(new TerrainBonus(effect));
        }

        // Recalculate synergy bonus (in case unit moved next to allies)
        unit.removeDecoratorsOfType(SynergyBonus.class);
        unit.addDecorator(new SynergyBonus(this, unit.getFaction()));
    }

    public ValidationResult executeCommand(Command command) {
        if (!currentPhase.canExecuteCommand(this, command)) {
            return ValidationResult.failure(
                    "Command not allowed in " + currentPhase.getPhaseName()
            );
        }

        return commandExecutor.execute(command, this);
    }

    public boolean undoLastCommand() {
        return commandExecutor.undo(this);
    }

    public boolean redoLastCommand() {
        return commandExecutor.redo(this);
    }

    public void handleUnitDeath(Unit unit) {
        removeUnit(unit);
        notifyObservers(new UnitDeathEvent(unit)); //Notify Observers before victory Check
        checkVictoryCondition();  // Check if game should end
    }

    public Unit getUnitAt(Position position) {
        return positionToUnit.get(position);
    }

    public List<Unit> getUnitsOfFaction(Faction faction) {
        return units.stream()
                .filter(u -> u.getFaction() == faction)
                .toList();
    }

    public List<Unit> getEnemiesOf(Faction faction) {
        return units.stream()
                .filter(u -> u.getFaction() != faction)
                .toList();
    }

    public List<Unit> getUnitsAdjacentTo(Position position) {
        return Arrays.stream(position.getAdjacentPositions())
                .filter(Objects::nonNull)
                .filter(this::isPositionOccupied)
                .map(this::getUnitAt)
                .filter(Objects::nonNull)
                .filter(Unit::isAlive)
                .toList();
    }

    public boolean canAttack(Unit attacker, Unit target) {
        return attacker.getAttackStrategy().canAttack(this, attacker, target);
    }

    public boolean isValidMove(Unit unit, Position target) {
        return unit.getMovementStrategy().canMoveTo(this, unit.getPosition(), target, unit.getBaseStats().movement());
    }

    public Terrain getTerrainAt(Position position) {
        return battlefield.getTerrainAt(position);
    }

    public int getTurnNumber() {
        return turnManager.getTurnNumber();
    }

    void setInitialActiveFaction(Faction faction) {
        this.activeFaction = faction;
    }

    private void ensureActiveFactionIsSet() {
        if (activeFaction == null) {
            activeFaction = determineFirstFaction();
        }
    }

    private Faction determineFirstFaction() {
        return units.stream()
                .map(Unit::getFaction)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No units on battlefield"));
    }

    private Faction getNextFaction() {
        // Simple round-robin
        Faction[] allFactions = units.stream()
                .filter(Unit::isAlive)
                .map(Unit::getFaction)
                .distinct()
                .toArray(Faction[]::new);

        for (int i = 0; i < allFactions.length; i++) {
            if (allFactions[i] == activeFaction) {
                return allFactions[(i + 1) % allFactions.length];
            }
        }

        return allFactions[0];
    }

    public void nextTurn() {
        List<Faction> aliveFactions = getAliveFactions();
        resetCurrentFactionUnits();
        applyPerTurnTerrainEffects();

        // Transition to next faction and notify
        endTurn(); // <- dispatched TurnEndedEvent
    }

    private void applyPerTurnTerrainEffects() {
        for (Unit unit : units) {
            if (!unit.isAlive()) {
                continue;
            }

            Terrain terrain = battlefield.getTerrainAt(unit.getPosition());
            TerrainVisitor visitor =
                TerrainVisitorFactory.getVisitor(terrain);
            TerrainEffectResult effect = unit.accept(visitor);

            if (effect.hasPerTurnEffect()) {
                int hpChange = effect.hpPerTurn();
                if (hpChange > 0) {
                    unit.heal(hpChange);
                } else {
                    unit.takeDamage(-hpChange); // Convert negative to positive
                }
            }
        }
    }

    private List<Faction> getAliveFactions() {
        return units.stream()
                .filter(Unit::isAlive)
                .map(Unit::getFaction)
                .distinct()
                .sorted()
                .toList();
    }

    private void resetCurrentFactionUnits() {
        getUnitsOfFaction(activeFaction).forEach(Unit::resetTurn);
        commandExecutor.clearHistory();
    }

    public Faction getWinner() {
        if (!isGameFinished()) {
            return null;
        }

        return findRemainingFaction();
    }

    private boolean isGameFinished() {
        return currentPhase instanceof GameOverPhase;
    }

    private Faction findRemainingFaction() {
        return getAliveFactions().stream()
                .findFirst()
                .orElse(null);
    }

    private static final GameRenderer RENDERER = new ConsoleGameRenderer();

    @Override
    public String toString() {
        return RENDERER.render(this);
    }

    // ===== OBSERVER PATTERN METHODS =====

    public void addObserver(GameObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(GameObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(GameEvent event) {
        for (GameObserver observer : observers) {
            observer.onEvent(event);
        }
    }

    public void notifyTerrainChanged(Position position, Terrain oldTerrain, Terrain newTerrain) {
        notifyObservers(new TerrainChangedEvent(position, oldTerrain, newTerrain));
    }
}
