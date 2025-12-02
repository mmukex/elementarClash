package units.factory;

import faction.Faction;
import units.Unit;
import units.UnitStats;
import units.UnitType;
import units.impl.fire.*;

/**
 * Concrete factory for creating Fire faction units.
 * Fire faction playstyle: Aggressive, high damage, synergy bonuses.
 */
public class FireUnitFactory extends UnitFactory {

    public FireUnitFactory() {
        super(Faction.FIRE);
    }

    @Override
    protected Unit createUnitInternal(String id, UnitType type) {
        return switch (type) {
            case INFERNO_WARRIOR -> new InfernoWarrior(
                    id,
                    new UnitStats(100, 15, 5, 3, 1)
            );
            case FLAME_ARCHER -> new FlameArcher(
                    id,
                    new UnitStats(70, 12, 3, 4, 3)
            );
            case PHOENIX -> new Phoenix(
                    id,
                    new UnitStats(80, 10, 4, 5, 1)
            );
            default -> throw new IllegalArgumentException("Unknown Fire unit type: " + type);
        };
    }

    @Override
    protected boolean isValidTypeForFaction(UnitType type) {
        return type == UnitType.INFERNO_WARRIOR
                || type == UnitType.FLAME_ARCHER
                || type == UnitType.PHOENIX;
    }
}
