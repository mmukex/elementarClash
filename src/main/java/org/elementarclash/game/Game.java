package org.elementarclash.game;

import lombok.Getter;
import org.elementarclash.battlefield.Battlefield;
import org.elementarclash.battlefield.Terrain;
import org.elementarclash.battlefield.visitor.TerrainEffectResult;
import org.elementarclash.battlefield.visitor.TerrainVisitor;
import org.elementarclash.battlefield.visitor.TerrainVisitorFactory;
import org.elementarclash.faction.Faction;
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
import org.elementarclash.units.decorator.SynergyBonus;
import org.elementarclash.units.decorator.TerrainBonus;
import org.elementarclash.util.Position;

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
    @Deprecated
    private GameStatus status;

    Game(Battlefield battlefield) {
        this.battlefield = battlefield;
        this.units = new ArrayList<>();
        this.positionToUnit = new HashMap<>();
        this.activeFaction = null;
        this.currentPhase = SetupPhase.getInstance();
        this.status = GameStatus.SETUP;
        this.commandExecutor = new CommandExecutor();
        this.turnManager = new TurnManager();
    }

    private void transitionToPhase(GamePhaseState newPhase) {
        currentPhase.onExit(this);
        this.currentPhase = newPhase;
        newPhase.onEnter(this);

        // Update deprecated enum for compatibility
        updateGameStatus();
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

        // PlayerTurn → EventPhase
        transitionToPhase(currentPhase.transitionToEventPhase(this));

        // EventPhase → next PlayerTurn
        Faction nextFaction = getNextFaction();
        transitionToPhase(currentPhase.transitionToPlayerTurn(this, nextFaction));
        this.activeFaction = nextFaction;
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

        positionToUnit.remove(unit.getPosition());
        positionToUnit.put(newPosition, unit);
        unit.setPosition(newPosition);

        applyTerrainTransformation(unit);
    }

    private void applyTerrainTransformation(Unit unit) {
        Terrain terrain = getTerrainAt(unit.getPosition());
        TerrainVisitor visitor = TerrainVisitorFactory.getVisitor(terrain);
        TerrainEffectResult effect = unit.accept(visitor);

        if (effect.terrainChange() != null) {
            battlefield.setTerrainAt(unit.getPosition(), effect.terrainChange());
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

        ValidationResult result = commandExecutor.execute(command, this);

        if (result.isValid() && currentPhase instanceof PlayerTurnPhase playerTurn) {
            playerTurn.incrementActionCount();
        }

        return result;
    }

    public boolean undoLastCommand() {
        return commandExecutor.undo(this);
    }

    public boolean redoLastCommand() {
        return commandExecutor.redo(this);
    }

    public boolean canUndo() {
        return commandExecutor.canUndo();
    }

    public boolean canRedo() {
        return commandExecutor.canRedo();
    }

    public void handleUnitDeath(Unit unit) {
        removeUnit(unit);
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

    public List<Unit> getUnitsInRange(Position position, int range) {
        return units.stream()
                .filter(u -> u.isAlive() && u.getPosition().isInRange(position, range))
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

    public List<Unit> getValidAttackTargets(Unit attacker) {
        return attacker.getAttackStrategy().getValidTargets(this, attacker);
    }

    public List<Position> getValidMovePositions(Unit unit) {
        List<Position> validPositions = new ArrayList<>();
        int maxMovement = unit.getBaseStats().movement();

        for (int y = 0; y < Battlefield.GRID_SIZE; y++) {
            for (int x = 0; x < Battlefield.GRID_SIZE; x++) {
                Position target = new Position(x, y);
                if (unit.getMovementStrategy().canMoveTo(this, unit.getPosition(), target, maxMovement)) {
                    validPositions.add(target);
                }
            }
        }
        return validPositions;
    }

    void setInitialActiveFaction(Faction faction) {
        this.activeFaction = faction;
    }

//    public void startGame() {
//        ensureActiveFactionIsSet();
//        this.status = GameStatus.IN_PROGRESS;
//        turnManager.startGame();
//    }

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
        activeFaction = determineNextFaction(aliveFactions);
        updateGameStatus();
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

    private Faction determineNextFaction(List<Faction> aliveFactions) {
        int currentIndex = aliveFactions.indexOf(activeFaction);
        int nextIndex = (currentIndex + 1) % aliveFactions.size();

        if (isNewRound(nextIndex)) {
            turnManager.incrementTurn();
        }

        return aliveFactions.get(nextIndex);
    }

    private boolean isNewRound(int nextIndex) {
        return nextIndex == 0;
    }

    private void updateGameStatus() {
        if (currentPhase instanceof SetupPhase) {
            status = GameStatus.SETUP;
        } else if (currentPhase instanceof GameOverPhase) {
            status = GameStatus.GAME_OVER;
        } else {
            status = GameStatus.IN_PROGRESS;
        }
    }

    private boolean isGameOver() {
        return getAliveFactions().size() <= SINGLE_FACTION_REMAINING;
    }

    public Faction getWinner() {
        if (!isGameFinished()) {
            return null;
        }

        return findRemainingFaction();
    }

    private boolean isGameFinished() {
        return status == GameStatus.GAME_OVER;
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

    public enum GameStatus {
        SETUP,
        IN_PROGRESS,
        GAME_OVER
    }
}
