package org.elementarclash.battlefield;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Defines five terrain types with their gameplay bonuses and distribution.
 * Each terrain affects defense, movement cost, and default battlefield distribution.
 * <p>
 * Why Enum: Finite set of terrain types with associated data and behavior.
 * Provides type-safety and prevents invalid terrain instances.
 *
 * @author mmukex
 */
@Getter
@RequiredArgsConstructor
public enum Terrain {
    LAVA("Lava", "🌋", 15),
    ICE("Eis", "❄️", 15),
    FOREST("Wald", "🌲", 20),
    DESERT("Wüste", "🌵", 30),
    STONE("Stein", "⛰️", 20);

    private final String germanName;
    private final String icon;
    private final int distributionPercent;

    public double getMovementCost() {
        return switch (this) {
            case FOREST, LAVA -> 2.0;
            case ICE -> 3.0;
            case STONE -> 3.0;
            default -> 1.0;
        };
    }
}
