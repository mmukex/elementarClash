package org.elementarclash.game.event;

public enum EventType {
    // Unit events
    UNIT_CREATED,
    UNIT_MOVED,
    UNIT_ATTACKED,
    UNIT_TOOK_DAMAGE,
    UNIT_HEALED,
    UNIT_DIED,
    UNIT_ABILITY_USED,

    // Terrain events
    TERRAIN_CHANGED,
    TERRAIN_EFFECT_APPLIED,

    // Game phase events
    TURN_STARTED,
    TURN_ENDED,
    GAME_STARTED,
    GAME_OVER
}