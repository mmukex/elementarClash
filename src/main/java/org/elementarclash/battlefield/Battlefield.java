package org.elementarclash.battlefield;

import org.elementarclash.game.Game;
import org.elementarclash.util.Position;

import java.util.*;

/**
 * Root composite representing the complete 10x10 battlefield with terrain.
 * Organized as hierarchy: Battlefield → 10 Rows → 100 Cells.
 * <p>
 * Design Pattern: Composite (GoF #3) - Root Composite
 * Why: Allows operations on entire battlefield while maintaining hierarchical structure.
 * Supports dynamic region extraction for localized effects.
 * <p>
 * Design Pattern: Builder (see GameBuilder for construction)
 *
 * @author mmukex
 */
public class Battlefield implements BattlefieldComponent {

    public static final int GRID_SIZE = 10;
    private static final int TOTAL_CELLS = GRID_SIZE * GRID_SIZE;
    private static final int PERCENTAGE_DIVISOR = 100;

    private final List<Region> rows;

    public Battlefield() {
        this.rows = new ArrayList<>(GRID_SIZE);
        initializeEmptyGrid();
    }

    private void initializeEmptyGrid() {
        for (int y = 0; y < GRID_SIZE; y++) {
            List<Cell> cells = new ArrayList<>(GRID_SIZE);
            for (int x = 0; x < GRID_SIZE; x++) {
                cells.add(new Cell(new Position(x, y), Terrain.DESERT));
            }
            rows.add(new Region(cells));
        }
    }

    public void initializeTerrain(Map<Terrain, Integer> distribution, Long randomSeed) {
        List<Terrain> terrainList = createTerrainList(distribution);
        fillRemainingCells(terrainList);
        shuffleTerrainList(terrainList, randomSeed);
        populateGrid(terrainList);
    }

    private List<Terrain> createTerrainList(Map<Terrain, Integer> distribution) {
        List<Terrain> terrainList = new ArrayList<>();

        for (Map.Entry<Terrain, Integer> entry : distribution.entrySet()) {
            int count = calculateTerrainCount(entry.getValue());
            addTerrainToList(terrainList, entry.getKey(), count);
        }

        return terrainList;
    }

    private int calculateTerrainCount(int percentage) {
        return (TOTAL_CELLS * percentage) / PERCENTAGE_DIVISOR;
    }

    private void addTerrainToList(List<Terrain> terrainList, Terrain terrain, int count) {
        for (int i = 0; i < count; i++) {
            terrainList.add(terrain);
        }
    }

    private void fillRemainingCells(List<Terrain> terrainList) {
        while (terrainList.size() < TOTAL_CELLS) {
            terrainList.add(Terrain.DESERT);
        }
    }

    private void shuffleTerrainList(List<Terrain> terrainList, Long randomSeed) {
        Random random = createRandom(randomSeed);
        Collections.shuffle(terrainList, random);
    }

    private Random createRandom(Long seed) {
        return seed != null ? new Random(seed) : new Random();
    }

    private void populateGrid(List<Terrain> terrainList) {
        int index = 0;
        for (int y = 0; y < GRID_SIZE; y++) {
            for (int x = 0; x < GRID_SIZE; x++) {
                getCell(x, y).setTerrain(terrainList.get(index++));
            }
        }
    }

    public Terrain getTerrainAt(Position position) {
        return getCell(position.x(), position.y()).getTerrain();
    }

    /**
     * Set terrain at position and notify game (for Observer Pattern).
     *
     * @author @crstmkt (Observer integration)
     */
    public void setTerrainAt(Position position, Terrain newTerrain, Game game) {
        Cell cell = getCell(position.x(), position.y());
        Terrain oldTerrain = cell.getTerrain();

        cell.setTerrain(newTerrain);

        if (game != null && oldTerrain != newTerrain) {
            game.notifyTerrainChanged(position, oldTerrain, newTerrain);
        }
    }

    // Keep for compatiblity
    public void setTerrainAt(Position position, Terrain newTerrain) {
        setTerrainAt(position, newTerrain, null);
    }

    public Cell getCell(int x, int y) {
        return rows.get(y).getCell(x);
    }

    public Region getRegion(int x1, int y1, int x2, int y2) {
        List<Cell> regionCells = new ArrayList<>();
        for (int y = y1; y <= y2; y++) {
            for (int x = x1; x <= x2; x++) {
                if (x >= 0 && x < GRID_SIZE && y >= 0 && y < GRID_SIZE) {
                    regionCells.add(getCell(x, y));
                }
            }
        }
        return new Region(regionCells);
    }

    /**
     * Get a random region for dynamic events.
     * Used by EventPhase for forest fires, geysers, etc.
     *
     * @author @crstmkt (integration helper for State Pattern)
     */
    public Region getRandomRegion() {
        if (rows.isEmpty()) {
            throw new IllegalStateException("No regions in battlefield");
        }
        int randomIndex = new Random().nextInt(rows.size());
        return rows.get(randomIndex);
    }

    @Override
    public List<Cell> cells() {
        return rows.stream()
                .flatMap(row -> row.cells().stream())
                .toList();
    }
}
