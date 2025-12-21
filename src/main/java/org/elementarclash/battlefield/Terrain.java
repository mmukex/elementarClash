package org.elementarclash.battlefield;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

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

    public int getDefenseBonus() {
        return switch (this) {
            case FOREST, STONE -> 2;
            case DESERT -> 1;
            default -> 0;
        };
    }

    public double getMovementCost() {
        return switch (this) {
            case FOREST, LAVA -> 1.5;
            case ICE -> 1.2;
            default -> 1.0;
        };
    }
}
