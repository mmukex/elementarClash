package org.elementarclash.faction;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Represents the four elemental factions in ElementarClash.
 * Each faction has unique units, playstyles, and elemental advantages.
 */
@Getter
@RequiredArgsConstructor
public enum Faction {
    FIRE("Feuer", "🔥", "Aggressiv"),
    WATER("Wasser", "💧", "Defensiv"),
    EARTH("Erde", "🪨", "Kontrollierend"),
    AIR("Luft", "💨", "Mobil");

    private final String germanName;
    private final String icon;
    private final String playstyle;
}