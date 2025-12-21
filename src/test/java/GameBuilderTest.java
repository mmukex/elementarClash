import org.elementarclash.game.Game;
import org.elementarclash.game.GameBuilder;
import org.elementarclash.battlefield.Terrain;
import org.elementarclash.faction.Faction;
import org.elementarclash.units.UnitFactory;
import org.elementarclash.units.UnitType;
import org.elementarclash.units.fire.FireUnitFactory;
import org.elementarclash.units.water.WaterUnitFactory;
import org.elementarclash.util.Position;

import java.util.Map;

/**
 * Demonstrates Builder Pattern with custom terrain distribution and random seed.
 */
public class GameBuilderTest {
    public static void main(String[] args) {
        UnitFactory fireFactory = new FireUnitFactory();
        UnitFactory waterFactory = new WaterUnitFactory();

        System.out.println("=== Test 1: Default Terrain Distribution ===");
        Game game1 = new GameBuilder()
                .withFactions(Faction.FIRE, Faction.WATER)
                .addUnit(fireFactory.createUnit(UnitType.INFERNO_WARRIOR), Faction.FIRE)
                .addUnit(waterFactory.createUnit(UnitType.TIDE_GUARDIAN), Faction.WATER)
                .build();

        game1.startGame();
        System.out.println(game1);

        System.out.println("\n=== Test 2: Custom Terrain (50% Lava, 50% Ice) ===");
        Game game2 = new GameBuilder()
                .withFactions(Faction.FIRE, Faction.WATER)
                .withCustomTerrain(Map.of(
                    Terrain.LAVA, 50,
                    Terrain.ICE, 50
                ))
                .addUnit(fireFactory.createUnit(UnitType.FLAME_ARCHER), Faction.FIRE)
                .addUnit(waterFactory.createUnit(UnitType.FROST_MAGE), Faction.WATER)
                .build();

        game2.startGame();
        System.out.println(game2);

        System.out.println("\n=== Test 3: Reproducible Terrain (Random Seed) ===");
        Game game3a = new GameBuilder()
                .withFactions(Faction.FIRE, Faction.WATER)
                .withRandomSeed(12345L)
                .addUnit(fireFactory.createUnit(UnitType.PHOENIX), Faction.FIRE)
                .addUnit(waterFactory.createUnit(UnitType.WAVE_RIDER), Faction.WATER)
                .build();

        Game game3b = new GameBuilder()
                .withFactions(Faction.FIRE, Faction.WATER)
                .withRandomSeed(12345L)
                .addUnit(fireFactory.createUnit(UnitType.PHOENIX), Faction.FIRE)
                .addUnit(waterFactory.createUnit(UnitType.WAVE_RIDER), Faction.WATER)
                .build();

        game3a.startGame();
        game3b.startGame();

        System.out.println("Game 3a (seed 12345):");
        System.out.println(game3a);

        System.out.println("\nGame 3b (seed 12345) - should be identical:");
        System.out.println(game3b);

        // Verify terrain is identical
        boolean identical = true;
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                Position pos = new Position(x, y);
                if (game3a.getBattlefield().getTerrainAt(pos) != game3b.getBattlefield().getTerrainAt(pos)) {
                    identical = false;
                    break;
                }
            }
        }
        System.out.println("Terrain identical: " + identical);
    }
}
