package org.elementarclash.game;

import org.elementarclash.battlefield.Terrain;
import org.elementarclash.units.Faction;
import org.elementarclash.units.Unit;
import org.elementarclash.units.UnitFactory;
import org.elementarclash.units.UnitType;
import org.elementarclash.units.types.air.AirUnitFactory;
import org.elementarclash.units.types.earth.EarthUnitFactory;
import org.elementarclash.units.types.fire.FireUnitFactory;
import org.elementarclash.units.types.water.WaterUnitFactory;

import java.util.*;

/**
 * Configures random game setups with random factions and terrain distributions.
 * Provides randomized game creation for varied gameplay experiences.
 */
public class RandomGameConfigurer {

    private static final int MIN_TERRAIN_PERCENT = 5;
    private static final int TOTAL_PERCENT = 100;

    private final Random random;

    private static final Map<Faction, List<UnitType>> UNIT_TYPES = Map.of(
            Faction.FIRE, List.of(UnitType.INFERNO_WARRIOR, UnitType.FLAME_ARCHER, UnitType.PHOENIX),
            Faction.WATER, List.of(UnitType.TIDE_GUARDIAN, UnitType.FROST_MAGE, UnitType.WAVE_RIDER),
            Faction.EARTH, List.of(UnitType.STONE_GOLEM, UnitType.TERRA_SHAMAN, UnitType.EARTHQUAKE_TITAN),
            Faction.AIR, List.of(UnitType.WIND_DANCER, UnitType.STORM_CALLER, UnitType.SKY_GUARDIAN)
    );

    public RandomGameConfigurer() {
        this.random = new Random();
    }

    public RandomGameConfigurer(long seed) {
        this.random = new Random(seed);
    }

    public Faction[] selectRandomFactions() {
        List<Faction> all = new ArrayList<>(Arrays.asList(Faction.values()));
        Collections.shuffle(all, random);
        return new Faction[]{all.get(0), all.get(1)};
    }

    public Map<Terrain, Integer> generateRandomTerrainDistribution() {
        Terrain[] terrains = Terrain.values();
        int terrainCount = terrains.length;
        int[] percentages = new int[terrainCount];

        int minTotal = terrainCount * MIN_TERRAIN_PERCENT;
        int remaining = TOTAL_PERCENT - minTotal;

        for (int i = 0; i < terrainCount; i++) {
            percentages[i] = MIN_TERRAIN_PERCENT;
        }

        for (int i = 0; i < remaining; i++) {
            int index = random.nextInt(terrainCount);
            percentages[index]++;
        }

        Map<Terrain, Integer> distribution = new EnumMap<>(Terrain.class);
        for (int i = 0; i < terrainCount; i++) {
            distribution.put(terrains[i], percentages[i]);
        }

        return distribution;
    }

    public Game createRandomGame() {
        Faction[] factions = selectRandomFactions();
        Map<Terrain, Integer> terrain = generateRandomTerrainDistribution();

        GameBuilder builder = new GameBuilder()
                .withFactions(factions)
                .withCustomTerrain(terrain);

        for (Faction faction : factions) {
            addUnitsForFaction(builder, faction);
        }

        return builder.build();
    }

    private void addUnitsForFaction(GameBuilder builder, Faction faction) {
        UnitFactory factory = switch (faction) {
            case FIRE -> new FireUnitFactory();
            case WATER -> new WaterUnitFactory();
            case EARTH -> new EarthUnitFactory();
            case AIR -> new AirUnitFactory();
        };

        for (UnitType type : UNIT_TYPES.get(faction)) {
            Unit unit = factory.createUnit(type);
            builder.addUnit(unit, faction);
        }
    }
}
