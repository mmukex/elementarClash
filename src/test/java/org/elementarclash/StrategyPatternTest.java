package org.elementarclash;

import org.elementarclash.battlefield.Terrain;
import org.elementarclash.game.Game;
import org.elementarclash.game.GameBuilder;
import org.elementarclash.units.Faction;
import org.elementarclash.units.Unit;
import org.elementarclash.units.UnitFactory;
import org.elementarclash.units.UnitType;
import org.elementarclash.units.strategy.attack.AttackStrategy;
import org.elementarclash.units.strategy.attack.MeleeAttackStrategy;
import org.elementarclash.units.strategy.attack.RangedAttackStrategy;
import org.elementarclash.units.strategy.movement.FlyingMovementStrategy;
import org.elementarclash.units.strategy.movement.GroundMovementStrategy;
import org.elementarclash.units.strategy.movement.MovementStrategy;
import org.elementarclash.units.types.air.AirUnitFactory;
import org.elementarclash.units.types.fire.FireUnitFactory;
import org.elementarclash.units.types.water.WaterUnitFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class StrategyPatternTest {

    private UnitFactory fireFactory;
    private UnitFactory waterFactory;
    private UnitFactory airFactory;

    @BeforeEach
    void setUp() {
        fireFactory = new FireUnitFactory();
        waterFactory = new WaterUnitFactory();
        airFactory = new AirUnitFactory();
    }

    @Test
    void groundMovementCostVariesByTerrain() {
        GroundMovementStrategy strategy = new GroundMovementStrategy(Faction.FIRE);

        double lavaCost = strategy.calculateMovementCost(Terrain.LAVA);
        double iceCost = strategy.calculateMovementCost(Terrain.ICE);
        double stoneCost = strategy.calculateMovementCost(Terrain.STONE);

        assertNotEquals(lavaCost, iceCost);
        assertNotEquals(lavaCost, stoneCost);
    }

    @Test
    void flyingMovementCostIsAlwaysOne() {
        FlyingMovementStrategy strategy = new FlyingMovementStrategy();

        assertEquals(1.0, strategy.calculateMovementCost(Terrain.LAVA));
        assertEquals(1.0, strategy.calculateMovementCost(Terrain.ICE));
        assertEquals(1.0, strategy.calculateMovementCost(Terrain.FOREST));
        assertEquals(1.0, strategy.calculateMovementCost(Terrain.DESERT));
        assertEquals(1.0, strategy.calculateMovementCost(Terrain.STONE));
    }

    @Test
    void groundAndFlyingStrategiesProduceDifferentCosts() {
        MovementStrategy ground = new GroundMovementStrategy(Faction.FIRE);
        MovementStrategy flying = new FlyingMovementStrategy();

        double groundIce = ground.calculateMovementCost(Terrain.ICE);
        double flyingIce = flying.calculateMovementCost(Terrain.ICE);

        assertNotEquals(groundIce, flyingIce);
        assertTrue(groundIce > flyingIce);
    }

    @Test
    void fireGroundMovementCheaperOnLava() {
        GroundMovementStrategy fireGround = new GroundMovementStrategy(Faction.FIRE);

        double lavaCost = fireGround.calculateMovementCost(Terrain.LAVA);
        double iceCost = fireGround.calculateMovementCost(Terrain.ICE);

        assertTrue(lavaCost < iceCost);
    }

    @Test
    void waterGroundMovementCheaperOnIce() {
        GroundMovementStrategy waterGround = new GroundMovementStrategy(Faction.WATER);

        double iceCost = waterGround.calculateMovementCost(Terrain.ICE);
        double lavaCost = waterGround.calculateMovementCost(Terrain.LAVA);

        assertTrue(iceCost < lavaCost);
    }

    @Test
    void sameFactionDifferentStrategiesAreBothMovementStrategy() {
        MovementStrategy ground = new GroundMovementStrategy(Faction.FIRE);
        MovementStrategy flying = new FlyingMovementStrategy();

        assertInstanceOf(MovementStrategy.class, ground);
        assertInstanceOf(MovementStrategy.class, flying);
    }

    @Test
    void meleeAndRangedAreBothAttackStrategy() {
        AttackStrategy melee = new MeleeAttackStrategy();
        AttackStrategy ranged = new RangedAttackStrategy(false);

        assertInstanceOf(AttackStrategy.class, melee);
        assertInstanceOf(AttackStrategy.class, ranged);
    }

    @Test
    void meleeAttackRangeIsUnitRange() {
        MeleeAttackStrategy melee = new MeleeAttackStrategy();
        Unit warrior = fireFactory.createUnit(UnitType.INFERNO_WARRIOR);

        int range = melee.getAttackRange(warrior);

        assertEquals(warrior.getBaseStats().range(), range);
    }

    @Test
    void rangedAttackRangeIsUnitRange() {
        RangedAttackStrategy ranged = new RangedAttackStrategy(false);
        Unit archer = fireFactory.createUnit(UnitType.FLAME_ARCHER);

        int range = ranged.getAttackRange(archer);

        assertEquals(archer.getBaseStats().range(), range);
        assertTrue(range > 1);
    }

    @Test
    void unitDefaultStrategiesAreLazyInitialized() {
        Unit warrior = fireFactory.createUnit(UnitType.INFERNO_WARRIOR);

        MovementStrategy movement = warrior.getMovementStrategy();
        AttackStrategy attack = warrior.getAttackStrategy();

        assertNotNull(movement);
        assertNotNull(attack);
    }

    @Test
    void airUnitUseFlyingMovementStrategy() {
        Unit windDancer = airFactory.createUnit(UnitType.WIND_DANCER);

        assertInstanceOf(FlyingMovementStrategy.class, windDancer.getMovementStrategy());
    }

    @Test
    void fireWarriorUsesGroundMovementStrategy() {
        Unit warrior = fireFactory.createUnit(UnitType.INFERNO_WARRIOR);

        assertInstanceOf(GroundMovementStrategy.class, warrior.getMovementStrategy());
    }

    @Test
    void flameArcherUsesRangedAttackStrategy() {
        Unit archer = fireFactory.createUnit(UnitType.FLAME_ARCHER);

        assertInstanceOf(RangedAttackStrategy.class, archer.getAttackStrategy());
    }

    @Test
    void canAttackReturnsFalseWhenTargetOutOfRange() {
        Game game = buildTwoFactionGame();
        game.startGame();

        Unit attacker = game.getUnitsOfFaction(Faction.FIRE).get(0);
        Unit target = game.getUnitsOfFaction(Faction.WATER).get(0);

        AttackStrategy strategy = attacker.getAttackStrategy();
        boolean result = strategy.canAttack(game, attacker, target);

        assertFalse(result);
    }

    @Test
    void differentFactionsHaveDifferentMovementCostsOnSameTerrain() {
        GroundMovementStrategy fire = new GroundMovementStrategy(Faction.FIRE);
        GroundMovementStrategy water = new GroundMovementStrategy(Faction.WATER);

        double fireLava = fire.calculateMovementCost(Terrain.LAVA);
        double waterLava = water.calculateMovementCost(Terrain.LAVA);

        assertNotEquals(fireLava, waterLava);
    }

    private Game buildTwoFactionGame() {
        GameBuilder builder = new GameBuilder()
                .withFactions(Faction.FIRE, Faction.WATER)
                .withRandomSeed(1L)
                .withCustomTerrain(Map.of(
                        Terrain.DESERT, 100,
                        Terrain.LAVA, 0, Terrain.ICE, 0,
                        Terrain.FOREST, 0, Terrain.STONE, 0
                ));
        builder.addUnit(fireFactory.createUnit(UnitType.INFERNO_WARRIOR), Faction.FIRE);
        builder.addUnit(waterFactory.createUnit(UnitType.TIDE_GUARDIAN), Faction.WATER);
        return builder.build();
    }
}
