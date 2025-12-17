package org.elementarclash.units.water;

import org.elementarclash.faction.Faction;
import org.elementarclash.units.Unit;
import org.elementarclash.units.UnitStats;
import org.elementarclash.units.UnitType;
import org.elementarclash.units.UnitFactory;

/**
 * Concrete factory for creating Water faction units.
 * Water faction playstyle: Defensive, crowd control, terrain synergy.
 */
public class WaterUnitFactory extends UnitFactory {

    public WaterUnitFactory() {
        super(Faction.WATER);
    }

    @Override
    protected Unit createUnitInternal(String id, UnitType type) {
        return switch (type) {
            case TIDE_GUARDIAN -> new TideGuardian(
                    id,
                    new UnitStats(120, 10, 8, 2, 1)
            );
            case FROST_MAGE -> new FrostMage(
                    id,
                    new UnitStats(60, 13, 4, 3, 4)
            );
            case WAVE_RIDER -> new WaveRider(
                    id,
                    new UnitStats(90, 11, 6, 4, 1)
            );
            default -> throw new IllegalArgumentException("Unknown Water unit type: " + type);
        };
    }

    @Override
    protected boolean isValidTypeForFaction(UnitType type) {
        return type == UnitType.TIDE_GUARDIAN
                || type == UnitType.FROST_MAGE
                || type == UnitType.WAVE_RIDER;
    }
}