package org.elementarclash.game.event.observer;

import org.elementarclash.game.Game;
import org.elementarclash.game.event.GameEvent;
import org.elementarclash.game.event.GameObserver;
import org.elementarclash.game.event.EventType;
import org.elementarclash.game.event.UnitDeathEvent;

/**
 * Observes unit deaths to check victory conditions.
 *  ToDo: Behalten oder weg? (weil Victory condition jetzt in Game.handleUnitDeath() geprüft wird. Siehe Zeile 24
 */
public class VictoryConditionObserver implements GameObserver {

    private final Game game;

    public VictoryConditionObserver(Game game) {
        this.game = game;
    }

    @Override
    public void onEvent(GameEvent event) {
        if (event.getEventType() == EventType.UNIT_DIED) {
            // Victory check is now handled in Game.handleUnitDeath()
            // This observer could trigger achievements, statistics, etc.
        }
    }

    @Override
    public String getObserverName() {
        return "VictoryConditionObserver";
    }
}