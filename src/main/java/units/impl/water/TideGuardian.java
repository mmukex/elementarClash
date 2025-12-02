package units.impl.water;

import faction.Faction;
import units.Unit;
import units.UnitStats;
import units.UnitType;

public class TideGuardian extends Unit {
    public TideGuardian(String id, UnitStats stats) {
        super(id, "Gezeiten-Wächter", Faction.WATER, UnitType.TIDE_GUARDIAN, stats);
    }

    @Override
    public String getSpecialAbility() {
        return "+3 Verteidigung auf Eis-Gelände";
    }
}