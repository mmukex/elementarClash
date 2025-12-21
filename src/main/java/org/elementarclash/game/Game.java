package org.elementarclash.game;

import lombok.Getter;
import org.elementarclash.battlefield.Battlefield;
import org.elementarclash.battlefield.Terrain;
import org.elementarclash.faction.Faction;
import org.elementarclash.units.Unit;
import org.elementarclash.util.Position;

import java.util.*;

/**
 * Central game manager for ElementarClash.
 * Manages units, game state, and coordinates with the battlefield.
 * Design Pattern: Builder (see GameBuilder for construction)
 */
@Getter
public class Game {

    private static final int INITIAL_ROUND = 0;
    private static final int STARTING_ROUND = 1;
    private static final int SINGLE_FACTION_REMAINING = 1;

    private final Battlefield battlefield;
    private final List<Unit> units;
    private final Map<Position, Unit> positionToUnit;

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

    public boolean moveUnit(Unit unit, Position newPosition) {
        if (!units.contains(unit) || isPositionOccupied(newPosition)) {
            return false;
        }

        positionToUnit.remove(unit.getPosition());
        positionToUnit.put(newPosition, unit);
        unit.setPosition(newPosition);

        return true;
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
        if (!attacker.isAlive() || !target.isAlive()) {
            return false;
        }

        if (attacker.getFaction() == target.getFaction()) {
            return false;
        }

        int attackRange = attacker.getBaseStats().range();
        return attacker.getPosition().isInRange(target.getPosition(), attackRange);
    }

    public boolean isValidMove(Unit unit, Position target) {
        if (isPositionOccupied(target)) {
            return false;
        }

        int maxMovement = unit.getBaseStats().movement();
        int distance = unit.getPosition().manhattanDistanceTo(target);

        return distance <= maxMovement;
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
        sb.append("\n\n");

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
