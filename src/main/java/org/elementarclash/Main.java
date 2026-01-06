package org.elementarclash;

import org.elementarclash.battlefield.Terrain;
import org.elementarclash.faction.Faction;
import org.elementarclash.game.Game;
import org.elementarclash.game.GameBuilder;
import org.elementarclash.ui.GameController;
import org.elementarclash.units.Unit;
import org.elementarclash.units.UnitType;
import org.elementarclash.units.fire.FireUnitFactory;
import org.elementarclash.units.water.WaterUnitFactory;

import java.util.Map;

/**
 * Main entry point for ElementarClash game.
 * Creates a default game setup and starts the interactive game loop.
 */
public class Main {

    public static void main(String[] args) {
        Game game = createDefaultGame();
        GameController controller = new GameController(game);
        controller.start();
    }

    private static Game createDefaultGame() {
        GameBuilder builder = new GameBuilder()
                .withFactions(Faction.FIRE, Faction.WATER)
                .withCustomTerrain(Map.of(
                        Terrain.LAVA, 20,
                        Terrain.ICE, 20,
                        Terrain.FOREST, 20,
                        Terrain.DESERT, 20,
                        Terrain.STONE, 20
                ));

        addFireUnits(builder);
        addWaterUnits(builder);

        return builder.build();
    }

    private static void addFireUnits(GameBuilder builder) {
        FireUnitFactory factory = new FireUnitFactory();

        Unit infernoWarrior = factory.createUnit(UnitType.INFERNO_WARRIOR);
        Unit flameArcher = factory.createUnit(UnitType.FLAME_ARCHER);
        Unit phoenix = factory.createUnit(UnitType.PHOENIX);

        builder.addUnit(infernoWarrior, Faction.FIRE);
        builder.addUnit(flameArcher, Faction.FIRE);
        builder.addUnit(phoenix, Faction.FIRE);
    }

    private static void addWaterUnits(GameBuilder builder) {
        WaterUnitFactory factory = new WaterUnitFactory();

        Unit tideGuardian = factory.createUnit(UnitType.TIDE_GUARDIAN);
        Unit frostMage = factory.createUnit(UnitType.FROST_MAGE);
        Unit waveRider = factory.createUnit(UnitType.WAVE_RIDER);

        builder.addUnit(tideGuardian, Faction.WATER);
        builder.addUnit(frostMage, Faction.WATER);
        builder.addUnit(waveRider, Faction.WATER);
    }
}
