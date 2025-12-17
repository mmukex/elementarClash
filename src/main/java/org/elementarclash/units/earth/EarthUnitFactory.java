package org.elementarclash.units.earth;

import org.elementarclash.faction.Faction;
import org.elementarclash.units.Unit;
import org.elementarclash.units.UnitStats;
import org.elementarclash.units.UnitType;
import org.elementarclash.units.UnitFactory;

/**
 * Concrete factory for creating Earth faction units.
 * Earth faction playstyle: Controlling, high defense, terrain manipulation.
 */
public class EarthUnitFactory extends UnitFactory {

    public EarthUnitFactory() {
        super(Faction.EARTH);
    }

    @Override
    protected Unit createUnitInternal(String id, UnitType type) {
        return switch (type) {
            case STONE_GOLEM -> new StoneGolem(
                    id,
                    new UnitStats(150, 8, 10, 2, 1)
            );
            case TERRA_SHAMAN -> new TerraShamane(
                    id,
                    new UnitStats(75, 11, 5, 3, 2)
            );
            case EARTHQUAKE_TITAN -> new EarthquakeTitan(
                    id,
                    new UnitStats(130, 14, 7, 2, 1)
            );
            default -> throw new IllegalArgumentException("Unknown Earth unit type: " + type);
        };
    }

    @Override
    protected boolean isValidTypeForFaction(UnitType type) {
        return type == UnitType.STONE_GOLEM
                || type == UnitType.TERRA_SHAMAN
                || type == UnitType.EARTHQUAKE_TITAN;
    }
}
