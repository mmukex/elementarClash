package org.elementarclash.game.event;

// ToDo: Ungenutze Events entweder implementieren  oder entfernen. Brauchen wir die ungenutzten Events noch?

public enum EventType {
    // Unit events
    // UNIT_CREATED, Maybe future implementation
    UNIT_MOVED,
    UNIT_ATTACKED,
    // UNIT_TOOK_DAMAGE, maybe future implementation
    // UNIT_HEALED, maybe future implementation
    UNIT_DIED,

    // Terrain events
    TERRAIN_CHANGED,
    // TERRAIN_EFFECT_APPLIED, maybe future implementation

    // Game phase events
    TURN_STARTED,
    TURN_ENDED,
    GAME_STARTED,
    GAME_OVER
}