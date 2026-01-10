package org.elementarclash.game;

import org.elementarclash.battlefield.Battlefield;
import org.elementarclash.battlefield.Terrain;
import org.elementarclash.faction.Faction;
import org.elementarclash.units.Unit;
import org.elementarclash.util.Position;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Builder for creating complex Game configurations.
 * Supports custom terrain, fair spawn positions, and validation.
 * <p>
 * Design Pattern: Builder (GoF #2)
 * Why: Game creation is complex with 100 cells terrain distribution,
 * spawn positions for 2-4 factions, and unit placement.
 * <p>
 * Usage:
 * <pre>
 * Game game = new GameBuilder()
 *     .withFactions(Faction.FIRE, Faction.WATER)
 *     .withCustomTerrain(Map.of(Terrain.LAVA, 50, Terrain.STONE, 50))
 *     .withRandomSeed(12345L)
 *     .addUnit(fireWarrior, Faction.FIRE)
 *     .addUnit(waterGuardian, Faction.WATER)
 *     .build();
 * </pre>
 *
 * @author mmukex
 */
public class GameBuilder {

    private static final int MIN_FACTIONS = 2;
    private static final int MAX_FACTIONS = 4;
    private static final int SPAWN_REGION_SIZE = 3;
    private static final int TOP_LEFT_X = 0;
    private static final int TOP_LEFT_Y = 0;
    private static final int TOP_RIGHT_X = 7;
    private static final int TOP_RIGHT_Y = 0;
    private static final int BOTTOM_LEFT_X = 0;
    private static final int BOTTOM_LEFT_Y = 7;
    private static final int BOTTOM_RIGHT_X = 7;
    private static final int BOTTOM_RIGHT_Y = 7;
    private static final int FIRST_FACTION_INDEX = 0;
    private static final int MAX_COORDINATE = 9;

    private final Set<Faction> factions = new LinkedHashSet<>();
    private final List<UnitPlacement> unitPlacements = new ArrayList<>();
    private Map<Terrain, Integer> customTerrainDistribution = null;
    private Long randomSeed = null;

    public GameBuilder withCustomTerrain(Map<Terrain, Integer> distribution) {
        int total = distribution.values().stream().mapToInt(Integer::intValue).sum();
        if (total != 100) {
            throw new IllegalArgumentException("Terrain distribution must sum to 100%, got: " + total + "%");
        }
        this.customTerrainDistribution = new EnumMap<>(distribution);
        return this;
    }

    public GameBuilder withRandomSeed(long seed) {
        this.randomSeed = seed;
        return this;
    }

    public GameBuilder withFactions(Faction... factions) {
        validateFactionCount(factions.length);
        this.factions.addAll(Arrays.asList(factions));
        return this;
    }

    private void validateFactionCount(int count) {
        if (count < MIN_FACTIONS || count > MAX_FACTIONS) {
            throw new IllegalArgumentException("Must have " + MIN_FACTIONS + "-" + MAX_FACTIONS + " factions, got: " + count);
        }
    }

    public GameBuilder addUnit(Unit unit, Faction faction) {
        if (!factions.contains(faction)) {
            throw new IllegalArgumentException("Faction " + faction + " not registered. Call withFactions() first.");
        }
        unitPlacements.add(new UnitPlacement(unit, faction));
        return this;
    }

    public Game build() {
        validate();

        Battlefield battlefield = createAndInitializeBattlefield();
        Game game = new Game(battlefield);

        placeUnitsOnBattlefield(game);
        setInitialFaction(game);

        return game;
    }

    private Battlefield createAndInitializeBattlefield() {
        Battlefield battlefield = new Battlefield();
        Map<Terrain, Integer> distribution = getTerrainDistribution();
        battlefield.initializeTerrain(distribution, randomSeed);
        return battlefield;
    }

    private void placeUnitsOnBattlefield(Game game) {
        Map<Faction, List<Position>> spawnZones = calculateSpawnZones(factions);

        for (UnitPlacement placement : unitPlacements) {
            Position spawnPos = findFreeSpawnPosition(game, spawnZones, placement.faction);
            game.addUnit(placement.unit, spawnPos);
        }
    }

    private Position findFreeSpawnPosition(Game game, Map<Faction, List<Position>> spawnZones, Faction faction) {
        List<Position> availableSpawns = spawnZones.get(faction);

        return availableSpawns.stream().filter(pos ->
                        !game.isPositionOccupied(pos))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No spawn position available for faction " + faction));
    }

    private void setInitialFaction(Game game) {
        Faction firstFaction = new ArrayList<>(factions).get(FIRST_FACTION_INDEX);
        game.setInitialActiveFaction(firstFaction);
    }

    private Map<Terrain, Integer> getTerrainDistribution() {
        return Objects.requireNonNullElseGet(customTerrainDistribution, () ->
                Arrays.stream(Terrain.values())
                        .collect(Collectors.toMap(terrain -> terrain, Terrain::getDistributionPercent)));
    }

    private void validate() {
        validateFactionsPresent();
        validateEachFactionHasUnits();
    }

    private void validateFactionsPresent() {
        if (factions.isEmpty()) {
            throw new IllegalStateException("No factions specified. Call withFactions().");
        }
    }

    private void validateEachFactionHasUnits() {
        Map<Faction, Long> unitsPerFaction = countUnitsPerFaction();

        for (Faction faction : factions) {
            if (!hasUnits(unitsPerFaction, faction)) {
                throw new IllegalStateException("Faction " + faction + " has no units. Each faction needs at least one unit.");
            }
        }
    }

    private Map<Faction, Long> countUnitsPerFaction() {
        return unitPlacements.stream().collect(Collectors.groupingBy(UnitPlacement::faction, Collectors.counting()));
    }

    private boolean hasUnits(Map<Faction, Long> unitsPerFaction, Faction faction) {
        return unitsPerFaction.containsKey(faction) && unitsPerFaction.get(faction) > 0;
    }

    private Map<Faction, List<Position>> calculateSpawnZones(Set<Faction> factions) {
        Map<Faction, List<Position>> spawnZones = new EnumMap<>(Faction.class);
        List<Faction> factionList = new ArrayList<>(factions);
        List<List<Position>> spawnRegions = createSpawnRegions();

        assignRegionsToFactions(spawnZones, factionList, spawnRegions);

        return spawnZones;
    }

    private List<List<Position>> createSpawnRegions() {
        return List.of(getSpawnRegion(TOP_LEFT_X, TOP_LEFT_Y),
                getSpawnRegion(TOP_RIGHT_X, TOP_RIGHT_Y),
                getSpawnRegion(BOTTOM_LEFT_X, BOTTOM_LEFT_Y),
                getSpawnRegion(BOTTOM_RIGHT_X, BOTTOM_RIGHT_Y));
    }

    private void assignRegionsToFactions(Map<Faction, List<Position>> spawnZones, List<Faction> factionList, List<List<Position>> spawnRegions) {
        for (int i = 0; i < factionList.size(); i++) {
            spawnZones.put(factionList.get(i), spawnRegions.get(i));
        }
    }

    private List<Position> getSpawnRegion(int startX, int startY) {
        List<Position> region = new ArrayList<>();

        for (int dx = 0; dx < SPAWN_REGION_SIZE; dx++) {
            for (int dy = 0; dy < SPAWN_REGION_SIZE; dy++) {
                int x = Math.min(MAX_COORDINATE, startX + dx);
                int y = Math.min(MAX_COORDINATE, startY + dy);
                region.add(new Position(x, y));
            }
        }

        return region;
    }

    private record UnitPlacement(Unit unit, Faction faction) {
    }
}
