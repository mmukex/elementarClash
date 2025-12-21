package org.elementarclash.battlefield;

import lombok.Getter;
import org.elementarclash.util.Position;

import java.util.*;

/**
 * Represents the 10x10 game board with terrain.
 * Design Pattern: Builder (see GameBuilder for construction)
 */
@Getter
public class Battlefield {

    public static final int GRID_SIZE = 10;
    private static final int TOTAL_CELLS = GRID_SIZE * GRID_SIZE;
    private static final int PERCENTAGE_DIVISOR = 100;

    private final Terrain[][] terrainGrid;

    public Battlefield() {
        this.terrainGrid = new Terrain[GRID_SIZE][GRID_SIZE];
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
                terrainGrid[y][x] = terrainList.get(index++);
            }
        }
    }

    public Terrain getTerrainAt(Position position) {
        return terrainGrid[position.y()][position.x()];
    }

    public void setTerrainAt(Position position, Terrain terrain) {
        terrainGrid[position.y()][position.x()] = terrain;
    }
}
