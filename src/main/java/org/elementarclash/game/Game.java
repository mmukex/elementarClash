package org.elementarclash.game;

import lombok.Getter;
import org.elementarclash.battlefield.Battlefield;
import org.elementarclash.battlefield.Terrain;
import org.elementarclash.faction.Faction;
import org.elementarclash.game.command.Command;
import org.elementarclash.game.command.CommandHistory;
import org.elementarclash.game.command.ValidationResult;
import org.elementarclash.units.Unit;
import org.elementarclash.units.UnitGroup;
import org.elementarclash.util.Position;

import java.util.*;

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

    private static final int INITIAL_ROUND = 0;
    private static final int STARTING_ROUND = 1;
    private static final int SINGLE_FACTION_REMAINING = 1;

    private final Battlefield battlefield;
    private final List<Unit> units;
    private final Map<Position, Unit> positionToUnit;
    private final CommandHistory commandHistory;
    private int currentRound;
    private Faction activeFaction;
    private GameStatus status;

    Game(Battlefield battlefield) {
        this.battlefield = battlefield;
        this.units = new ArrayList<>();
        this.positionToUnit = new HashMap<>();
        this.currentRound = INITIAL_ROUND;
        this.activeFaction = null;
        this.status = GameStatus.SETUP;
        this.commandHistory = new CommandHistory();
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

    public boolean moveUnitInternal(Unit unit, Position newPosition) {
        if (!units.contains(unit) || isPositionOccupied(newPosition)) {
            return false;
        }

        positionToUnit.remove(unit.getPosition());
        positionToUnit.put(newPosition, unit);
        unit.setPosition(newPosition);

        return true;
    }

    public ValidationResult executeCommand(Command command) {
        ValidationResult result = command.validate(this);
        if (!result.isValid()) {
            return result;
        }

        try {
            command.execute(this);
            commandHistory.push(command);
            return ValidationResult.success();
        } catch (Exception e) {
            return ValidationResult.failure("Execution failed: " + e.getMessage());
        }
    }

    public boolean undoLastCommand() {
        Command command = commandHistory.popForUndo();
        if (command == null) {
            return false;
        }

        try {
            command.undo(this);
            return true;
        } catch (Exception e) {
            commandHistory.push(command);
            throw new IllegalStateException("Undo failed: " + e.getMessage(), e);
        }
    }

    public boolean redoLastCommand() {
        Command command = commandHistory.popForRedo();
        if (command == null) {
            return false;
        }

        try {
            command.execute(this);
            return true;
        } catch (Exception e) {
            commandHistory.pushUndone(command);
            throw new IllegalStateException("Redo failed: " + e.getMessage(), e);
        }
    }

    public boolean canUndo() {
        return commandHistory.canUndo();
    }

    public boolean canRedo() {
        return commandHistory.canRedo();
    }

    /**
     * Handles unit death.
     * Called by AttackCommand when target health reaches 0.
     * Does not remove unit from list to preserve undo capability.
     *
     * @param unit unit that died
     */
    public void handleUnitDeath(Unit unit) {
        // Future: trigger death effects, check victory conditions
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

    public UnitGroup getUnitGroupOfFaction(Faction faction) {
        return new UnitGroup(getUnitsOfFaction(faction));
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

    public void startGame() {
        ensureActiveFactionIsSet();
        this.status = GameStatus.IN_PROGRESS;
        this.currentRound = STARTING_ROUND;
    }

    private void ensureActiveFactionIsSet() {
        if (activeFaction == null) {
            activeFaction = findFirstFactionWithUnits();
        }
    }

    private Faction findFirstFactionWithUnits() {
        return units.stream()
                .map(Unit::getFaction)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No units on battlefield"));
    }

    public void nextTurn() {
        List<Faction> aliveFactions = getAliveFactions();
        resetCurrentFactionUnits();
        activeFaction = determineNextFaction(aliveFactions);
        updateGameStatus();
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
        commandHistory.clear();
    }

    private Faction determineNextFaction(List<Faction> aliveFactions) {
        int currentIndex = aliveFactions.indexOf(activeFaction);
        int nextIndex = (currentIndex + 1) % aliveFactions.size();

        if (isNewRound(nextIndex)) {
            currentRound++;
        }

        return aliveFactions.get(nextIndex);
    }

    private boolean isNewRound(int nextIndex) {
        return nextIndex == 0;
    }

    private void updateGameStatus() {
        if (isGameOver()) {
            status = GameStatus.GAME_OVER;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("################################################################################################").append(System.lineSeparator());
        sb.append("               ELEMENTARCLASH - Runde ").append(currentRound).append(System.lineSeparator());
        sb.append("################################################################################################").append(System.lineSeparator());

        if (activeFaction != null) {
            sb.append("Aktive Fraktion: ").append(activeFaction.getGermanName())
                    .append(" ").append(activeFaction.getIcon()).append(System.lineSeparator());
        }

        sb.append("Status: ").append(status).append(System.lineSeparator());

        // Grid header - column numbers centered over emojis
        sb.append("   ");
        for (int x = 0; x < Battlefield.GRID_SIZE; x++) {
            sb.append("  ").append(x).append("  ");
        }
        sb.append(System.lineSeparator());

        // Grid rows
        for (int y = 0; y < Battlefield.GRID_SIZE; y++) {
            sb.append(y).append(" |");
            for (int x = 0; x < Battlefield.GRID_SIZE; x++) {
                Position pos = new Position(x, y);
                Unit unit = getUnitAt(pos);

                if (unit != null && unit.isAlive()) {
                    sb.append(" ").append(unit.getFaction().getIcon()).append(" |");
                } else {
                    sb.append(" ").append(battlefield.getTerrainAt(pos).getIcon()).append(" |");
                }
            }
            sb.append(System.lineSeparator()).append(System.lineSeparator());
        }


        // Legend
        sb.append(System.lineSeparator()).append("Legende:");
        for (Terrain terrain : Terrain.values()) {
            sb.append("  ").append(terrain.getIcon()).append(" ")
                    .append(terrain.getGermanName()).append(" | ");
        }
        sb.append(System.lineSeparator()).append(System.lineSeparator());

        // Unit summary
        long livingUnits = units.stream().filter(Unit::isAlive).count();
        sb.append("Lebende Einheiten: ").append(livingUnits).append("/").append(units.size()).append(System.lineSeparator());

        return sb.toString();
    }

    public enum GameStatus {
        SETUP,
        IN_PROGRESS,
        GAME_OVER
    }
}
