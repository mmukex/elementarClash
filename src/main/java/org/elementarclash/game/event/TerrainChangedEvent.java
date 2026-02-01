package org.elementarclash.game.event;

import lombok.Getter;
import org.elementarclash.battlefield.Terrain;
import org.elementarclash.util.Position;

@Getter
public class TerrainChangedEvent extends GameEvent {

    private final Position position;
    private final Terrain oldTerrain;
    private final Terrain newTerrain;

    public TerrainChangedEvent(Position position, Terrain oldTerrain, Terrain newTerrain) {
        super();
        this.position = position;
        this.oldTerrain = oldTerrain;
        this.newTerrain = newTerrain;
    }

    @Override
    public EventType getEventType() {
        return EventType.TERRAIN_CHANGED;
    }

    @Override
    public String getDescription() {
        return "Terrain at " + position + " changed from " +
                oldTerrain.getGermanName() + " to " + newTerrain.getGermanName();
    }

}