package org.elementarclash;

import org.elementarclash.battlefield.Terrain;
import org.elementarclash.game.Game;
import org.elementarclash.game.GameBuilder;
import org.elementarclash.units.Faction;
import org.elementarclash.units.Unit;
import org.elementarclash.units.UnitFactory;
import org.elementarclash.units.UnitType;
import org.elementarclash.units.types.fire.FireUnitFactory;
import org.elementarclash.units.types.water.WaterUnitFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class BuilderPatternTest {

    private GameBuilder builder;
    private UnitFactory fireFactory;
    private UnitFactory waterFactory;

    @BeforeEach
    void setUp() {
        builder = new GameBuilder();
        fireFactory = new FireUnitFactory();
        waterFactory = new WaterUnitFactory();
    }

    @Test
    void withFactionsReturnsSameBuilderForChaining() {
        GameBuilder result = builder.withFactions(Faction.FIRE, Faction.WATER);
        assertSame(builder, result);
    }

    @Test
    void withCustomTerrainReturnsSameBuilderForChaining() {
        Map<Terrain, Integer> dist = Map.of(
                Terrain.LAVA, 20, Terrain.ICE, 20, Terrain.FOREST, 20,
                Terrain.DESERT, 20, Terrain.STONE, 20
        );
        GameBuilder result = builder.withCustomTerrain(dist);
        assertSame(builder, result);
    }

    @Test
    void withRandomSeedReturnsSameBuilderForChaining() {
        GameBuilder result = builder.withRandomSeed(42L);
        assertSame(builder, result);
    }

    @Test
    void fluentChainingOfConfigurationMethods() {
        GameBuilder result = builder
                .withFactions(Faction.FIRE, Faction.WATER)
                .withRandomSeed(99L)
                .withCustomTerrain(Map.of(
                        Terrain.LAVA, 20, Terrain.ICE, 20, Terrain.FOREST, 20,
                        Terrain.DESERT, 20, Terrain.STONE, 20
                ));
        assertSame(builder, result);
    }

    @Test
    void buildCreatesNonNullGameWithBattlefield() {
        builder.withFactions(Faction.FIRE, Faction.WATER);
        builder.addUnit(fireFactory.createUnit(UnitType.INFERNO_WARRIOR), Faction.FIRE);
        builder.addUnit(waterFactory.createUnit(UnitType.TIDE_GUARDIAN), Faction.WATER);

        Game game = builder.build();

        assertNotNull(game);
        assertNotNull(game.getBattlefield());
    }

    @Test
    void buildCreates10x10GridWith100Cells() {
        builder.withFactions(Faction.FIRE, Faction.WATER).withRandomSeed(1L);
        builder.addUnit(fireFactory.createUnit(UnitType.INFERNO_WARRIOR), Faction.FIRE);
        builder.addUnit(waterFactory.createUnit(UnitType.TIDE_GUARDIAN), Faction.WATER);

        Game game = builder.build();

        assertEquals(100, game.getBattlefield().cells().size());
    }

    @Test
    void buildPlacesAllUnitsOnBattlefield() {
        builder.withFactions(Faction.FIRE, Faction.WATER);
        Unit warrior = fireFactory.createUnit(UnitType.INFERNO_WARRIOR);
        Unit guardian = waterFactory.createUnit(UnitType.TIDE_GUARDIAN);
        builder.addUnit(warrior, Faction.FIRE);
        builder.addUnit(guardian, Faction.WATER);

        Game game = builder.build();

        assertEquals(2, game.getUnits().size());
        assertNotNull(warrior.getPosition());
        assertNotNull(guardian.getPosition());
    }

    @Test
    void buildWithCustomTerrainDistribution100PercentLava() {
        Map<Terrain, Integer> allLava = Map.of(
                Terrain.LAVA, 100, Terrain.ICE, 0, Terrain.FOREST, 0,
                Terrain.DESERT, 0, Terrain.STONE, 0
        );
        builder.withFactions(Faction.FIRE, Faction.WATER).withCustomTerrain(allLava);
        builder.addUnit(fireFactory.createUnit(UnitType.INFERNO_WARRIOR), Faction.FIRE);
        builder.addUnit(waterFactory.createUnit(UnitType.TIDE_GUARDIAN), Faction.WATER);

        Game game = builder.build();

        long lavaCount = game.getBattlefield().cells().stream()
                .filter(c -> c.getTerrain() == Terrain.LAVA)
                .count();
        assertEquals(100, lavaCount);
    }

    @Test
    void buildWithSameSeedProducesIdenticalTerrain() {
        GameBuilder b1 = new GameBuilder()
                .withFactions(Faction.FIRE, Faction.WATER)
                .withRandomSeed(42L);
        b1.addUnit(fireFactory.createUnit(UnitType.INFERNO_WARRIOR), Faction.FIRE);
        b1.addUnit(waterFactory.createUnit(UnitType.TIDE_GUARDIAN), Faction.WATER);

        UnitFactory fire2 = new FireUnitFactory();
        UnitFactory water2 = new WaterUnitFactory();
        GameBuilder b2 = new GameBuilder()
                .withFactions(Faction.FIRE, Faction.WATER)
                .withRandomSeed(42L);
        b2.addUnit(fire2.createUnit(UnitType.INFERNO_WARRIOR), Faction.FIRE);
        b2.addUnit(water2.createUnit(UnitType.TIDE_GUARDIAN), Faction.WATER);

        Game game1 = b1.build();
        Game game2 = b2.build();

        for (int i = 0; i < 100; i++) {
            assertEquals(
                    game1.getBattlefield().cells().get(i).getTerrain(),
                    game2.getBattlefield().cells().get(i).getTerrain()
            );
        }
    }

    @Test
    void withFactionsRejectsFewerThanTwoFactions() {
        assertThrows(IllegalArgumentException.class, () ->
                builder.withFactions(Faction.FIRE)
        );
    }

    @Test
    void buildFailsWhenFactionHasNoUnits() {
        builder.withFactions(Faction.FIRE, Faction.WATER);
        builder.addUnit(fireFactory.createUnit(UnitType.INFERNO_WARRIOR), Faction.FIRE);
        assertThrows(IllegalStateException.class, () -> builder.build());
    }

    @Test
    void buildFailsWhenTerrainDistributionNotSumTo100() {
        Map<Terrain, Integer> badDist = Map.of(
                Terrain.LAVA, 50, Terrain.ICE, 10, Terrain.FOREST, 10,
                Terrain.DESERT, 10, Terrain.STONE, 10
        );
        assertThrows(IllegalArgumentException.class, () -> builder.withCustomTerrain(badDist));
    }

    @Test
    void buildFailsWithNoFactions() {
        assertThrows(IllegalStateException.class, () -> builder.build());
    }
}
