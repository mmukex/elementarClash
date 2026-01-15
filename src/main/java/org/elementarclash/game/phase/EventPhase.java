package org.elementarclash.game.phase;

import org.elementarclash.battlefield.Region;
import org.elementarclash.battlefield.Terrain;
import org.elementarclash.game.Game;
import org.elementarclash.game.command.Command;
import org.elementarclash.units.Faction;
import org.elementarclash.units.Unit;

import java.util.Random;

/**
 * Event phase: Dynamic events occur (Forest Fires, Geysers, Earthquakes).
 * No player commands allowed.
 *
 * Integration Point: Uses @mmukex Composite Pattern (battlefield.applyEffect)
 */
public class EventPhase implements GamePhaseState {

    private static final EventPhase INSTANCE = new EventPhase();
    private static final Random RANDOM = new Random();

    public static EventPhase getInstance() {
        return INSTANCE;
    }

    private EventPhase() {}

    @Override
    public boolean canExecuteCommand(Game game, Command command) {
        return false; // No commands during events
    }

    @Override
    public void onEnter(Game game) {
        executeRandomEvent(game);
    }

    @Override
    public void onExit(Game game) {
        // No special logic
    }

    /**
     * Execute a random dynamic event.
     * Integration with @mmukex Composite Pattern!
     */
    private void executeRandomEvent(Game game) {
        int eventType = RANDOM.nextInt(3);

        switch (eventType) {
            case 0 -> executeForestFire(game);
            case 1 -> executeGeyser(game);
            case 2 -> executeEarthquake(game);
        }
    }

    /**
     * Forest Fire: Random forest region burns to lava.
     * Uses @mmukex Composite: region.applyEffect()
     */
    private void executeForestFire(Game game) {
        Region randomRegion = game.getBattlefield().getRandomRegion();

        // INTEGRATION WITH @mmukex COMPOSITE PATTERN!
        randomRegion.applyEffect(cell -> {
            if (cell.getTerrain() == Terrain.FOREST) {
                cell.setTerrain(Terrain.LAVA);

                // Damage units on affected cells
                Unit unit = game.getUnitAt(cell.getPosition());
                if (unit != null && unit.isAlive()) {
                    unit.takeDamage(10);
                    System.out.println("[Event] Forest Fire! " + unit.getName() + " took 10 fire damage.");
                }
            }
        });

        System.out.println("[Event] Forest Fire erupted in region!");
    }

    /**
     * Geyser: Random ice region erupts, damaging units.
     */
    private void executeGeyser(Game game) {
        Region randomRegion = game.getBattlefield().getRandomRegion();

        randomRegion.applyEffect(cell -> {
            if (cell.getTerrain() == Terrain.ICE) {
                Unit unit = game.getUnitAt(cell.getPosition());
                if (unit != null && unit.isAlive()) {
                    unit.takeDamage(8);
                    System.out.println("[Event] Geyser! " + unit.getName() + " took 8 water damage.");
                }
            }
        });

        System.out.println("[Event] Geysers erupted!");
    }

    /**
     * Earthquake: Random stone cells crack, units are stunned.
     */
    private void executeEarthquake(Game game) {
        Region randomRegion = game.getBattlefield().getRandomRegion();

        randomRegion.applyEffect(cell -> {
            if (cell.getTerrain() == Terrain.STONE) {
                Unit unit = game.getUnitAt(cell.getPosition());
                if (unit != null && unit.isAlive()) {
                    unit.stun(1);  // Uses Unit State Pattern!
                    System.out.println("[Event] Earthquake! " + unit.getName() + " is stunned for 1 turn.");
                }
            }
        });

        System.out.println("[Event] Earthquake shook the battlefield!");
    }

    @Override
    public GamePhaseState transitionToPlayerTurn(Game game, Faction faction) {
        return new PlayerTurnPhase(faction);
    }

    @Override
    public GamePhaseState transitionToEventPhase(Game game) {
        return this; // Already in event phase
    }

    @Override
    public GamePhaseState transitionToGameOver(Game game, Faction winner) {
        return new GameOverPhase(winner);
    }

    @Override
    public String getPhaseName() {
        return "EventPhase";
    }
}