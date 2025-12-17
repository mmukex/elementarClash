package org.elementarclash.units.air;

import org.elementarclash.faction.Faction;
import org.elementarclash.units.Unit;
import org.elementarclash.units.UnitStats;
import org.elementarclash.units.UnitType;
import org.elementarclash.units.UnitFactory;

/**
 * Concrete factory for creating Air faction units.
 * Air faction playstyle: Mobile, evasive, high movement and initiative.
 */
public class AirUnitFactory extends UnitFactory {

    public AirUnitFactory() {
        super(Faction.AIR);
    }

    @Override
    protected Unit createUnitInternal(String id, UnitType type) {
        return switch (type) {
            case WIND_DANCER -> new WindDancer(
                    id,
                    new UnitStats(70, 12, 3, 6, 1)
            );
            case STORM_CALLER -> new StormCaller(
                    id,
                    new UnitStats(65, 14, 2, 4, 3)
            );
            case SKY_GUARDIAN -> new SkyGuardian(
                    id,
                    new UnitStats(85, 10, 5, 5, 2)
            );
            default -> throw new IllegalArgumentException("Unknown Air unit type: " + type);
        };
    }

    @Override
    protected boolean isValidTypeForFaction(UnitType type) {
        return type == UnitType.WIND_DANCER
                || type == UnitType.STORM_CALLER
                || type == UnitType.SKY_GUARDIAN;
    }
}
