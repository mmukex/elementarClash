package org.elementarclash.units;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Represents the four elemental factions in ElementarClash.
 * Each faction has unique units, playstyles, and elemental advantages.
 */
@Getter
@RequiredArgsConstructor
public enum Faction {
    FIRE("Feuer", "F", "Aggressiv"),
    WATER("Wasser", "W", "Defensiv"),
    EARTH("Erde", "E", "Kontrollierend"),
    AIR("Luft", "A", "Mobil");

    private final String germanName;
    private final String icon;
    private final String playstyle;
}