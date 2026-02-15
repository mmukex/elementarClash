package org.elementarclash;

import org.elementarclash.battlefield.Terrain;
import org.elementarclash.battlefield.terraineffect.*;
import org.elementarclash.units.Faction;
import org.elementarclash.units.Unit;
import org.elementarclash.units.UnitFactory;
import org.elementarclash.units.UnitType;
import org.elementarclash.units.types.air.AirUnitFactory;
import org.elementarclash.units.types.earth.EarthUnitFactory;
import org.elementarclash.units.types.fire.FireUnitFactory;
import org.elementarclash.units.types.water.WaterUnitFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VisitorPatternTest {

    private Unit fireUnit;
    private Unit waterUnit;
    private Unit earthUnit;
    private Unit airUnit;

    @BeforeEach
    void setUp() {
        fireUnit = new FireUnitFactory().createUnit(UnitType.INFERNO_WARRIOR);
        waterUnit = new WaterUnitFactory().createUnit(UnitType.TIDE_GUARDIAN);
        earthUnit = new EarthUnitFactory().createUnit(UnitType.STONE_GOLEM);
        airUnit = new AirUnitFactory().createUnit(UnitType.WIND_DANCER);
    }

    @Test
    void lavaVisitorFireUnitGetsAttackBonus() {
        TerrainVisitor lava = new LavaTerrainVisitor();
        TerrainEffectResult result = fireUnit.accept(lava);

        assertEquals(2, result.attackBonus());
        assertEquals(0, result.defenseBonus());
        assertEquals(0, result.hpPerTurn());
    }

    @Test
    void lavaVisitorWaterUnitGetsHpDrain() {
        TerrainVisitor lava = new LavaTerrainVisitor();
        TerrainEffectResult result = waterUnit.accept(lava);

        assertEquals(0, result.attackBonus());
        assertEquals(-5, result.hpPerTurn());
        assertTrue(result.hasPerTurnEffect());
    }

    @Test
    void lavaVisitorEarthUnitGetsNeutral() {
        TerrainVisitor lava = new LavaTerrainVisitor();
        TerrainEffectResult result = earthUnit.accept(lava);

        assertEquals(0, result.attackBonus());
        assertEquals(0, result.defenseBonus());
        assertEquals(0, result.hpPerTurn());
    }

    @Test
    void lavaVisitorAirUnitGetsNeutral() {
        TerrainVisitor lava = new LavaTerrainVisitor();
        TerrainEffectResult result = airUnit.accept(lava);

        assertEquals(0, result.attackBonus());
        assertEquals(0, result.defenseBonus());
        assertEquals(0, result.hpPerTurn());
    }

    @Test
    void doubleDispatchSameVisitorDifferentResultsPerFaction() {
        TerrainVisitor lava = new LavaTerrainVisitor();

        TerrainEffectResult fireResult = fireUnit.accept(lava);
        TerrainEffectResult waterResult = waterUnit.accept(lava);

        assertNotEquals(fireResult.attackBonus(), waterResult.attackBonus());
        assertNotEquals(fireResult.hpPerTurn(), waterResult.hpPerTurn());
    }

    @Test
    void doubleDispatchSameUnitDifferentVisitorsGiveDifferentResults() {
        TerrainVisitor lava = new LavaTerrainVisitor();
        TerrainVisitor desert = new DesertTerrainVisitor();

        TerrainEffectResult lavaResult = fireUnit.accept(lava);
        TerrainEffectResult desertResult = fireUnit.accept(desert);

        assertNotEquals(lavaResult.attackBonus(), desertResult.attackBonus());
    }

    @Test
    void desertVisitorReturnsNeutralForAllFactions() {
        TerrainVisitor desert = new DesertTerrainVisitor();

        assertEquals(TerrainEffectResult.NEUTRAL, fireUnit.accept(desert));
        assertEquals(TerrainEffectResult.NEUTRAL, waterUnit.accept(desert));
        assertEquals(TerrainEffectResult.NEUTRAL, earthUnit.accept(desert));
        assertEquals(TerrainEffectResult.NEUTRAL, airUnit.accept(desert));
    }

    @Test
    void stoneVisitorEarthUnitGetsDefenseBonus() {
        TerrainVisitor stone = new StoneTerrainVisitor();
        TerrainEffectResult result = earthUnit.accept(stone);

        assertEquals(2, result.defenseBonus());
    }

    @Test
    void stoneVisitorFireUnitGetsNeutral() {
        TerrainVisitor stone = new StoneTerrainVisitor();
        TerrainEffectResult result = fireUnit.accept(stone);

        assertEquals(0, result.defenseBonus());
        assertEquals(0, result.attackBonus());
    }

    @Test
    void iceVisitorWaterUnitGetsDefenseAndHealing() {
        TerrainVisitor ice = new IceTerrainVisitor();
        TerrainEffectResult result = waterUnit.accept(ice);

        assertTrue(result.defenseBonus() > 0);
        assertTrue(result.hpPerTurn() > 0);
        assertTrue(result.hasPerTurnEffect());
    }

    @Test
    void forestVisitorGivesDefenseBonusToAllFactions() {
        TerrainVisitor forest = new ForestTerrainVisitor();

        assertTrue(fireUnit.accept(forest).defenseBonus() > 0);
        assertTrue(waterUnit.accept(forest).defenseBonus() > 0);
        assertTrue(earthUnit.accept(forest).defenseBonus() > 0);
        assertTrue(airUnit.accept(forest).defenseBonus() > 0);
    }

    @Test
    void terrainEffectResultNeutralHasAllZeros() {
        TerrainEffectResult neutral = TerrainEffectResult.NEUTRAL;

        assertEquals(0, neutral.attackBonus());
        assertEquals(0, neutral.defenseBonus());
        assertEquals(0, neutral.hpPerTurn());
        assertNull(neutral.terrainChange());
        assertFalse(neutral.hasPerTurnEffect());
    }

    @Test
    void terrainVisitorFactoryReturnsVisitorForEachTerrain() {
        for (Terrain terrain : Terrain.values()) {
            TerrainVisitor visitor = TerrainVisitorFactory.getVisitor(terrain);
            assertNotNull(visitor);
        }
    }

    @Test
    void terrainVisitorFactoryReturnsSameInstanceForSameTerrain() {
        TerrainVisitor v1 = TerrainVisitorFactory.getVisitor(Terrain.LAVA);
        TerrainVisitor v2 = TerrainVisitorFactory.getVisitor(Terrain.LAVA);

        assertSame(v1, v2);
    }

    @Test
    void terrainVisitorFactoryReturnsCorrectVisitorType() {
        assertInstanceOf(LavaTerrainVisitor.class, TerrainVisitorFactory.getVisitor(Terrain.LAVA));
        assertInstanceOf(IceTerrainVisitor.class, TerrainVisitorFactory.getVisitor(Terrain.ICE));
        assertInstanceOf(ForestTerrainVisitor.class, TerrainVisitorFactory.getVisitor(Terrain.FOREST));
        assertInstanceOf(DesertTerrainVisitor.class, TerrainVisitorFactory.getVisitor(Terrain.DESERT));
        assertInstanceOf(StoneTerrainVisitor.class, TerrainVisitorFactory.getVisitor(Terrain.STONE));
    }

    @Test
    void acceptReturnsTerrainEffectResultNotNull() {
        TerrainVisitor visitor = TerrainVisitorFactory.getVisitor(Terrain.LAVA);

        assertNotNull(fireUnit.accept(visitor));
        assertNotNull(waterUnit.accept(visitor));
        assertNotNull(earthUnit.accept(visitor));
        assertNotNull(airUnit.accept(visitor));
    }

    @Test
    void allFiveTerrainsFourFactionsProduceValidResults() {
        Unit[] units = {fireUnit, waterUnit, earthUnit, airUnit};

        for (Terrain terrain : Terrain.values()) {
            TerrainVisitor visitor = TerrainVisitorFactory.getVisitor(terrain);
            for (Unit unit : units) {
                TerrainEffectResult result = unit.accept(visitor);
                assertNotNull(result);
                assertNotNull(result.effectDescription());
            }
        }
    }
}
