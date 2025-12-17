package org.elementarclash.units.water;

import org.elementarclash.faction.Faction;
import org.elementarclash.units.Unit;
import org.elementarclash.units.UnitStats;
import org.elementarclash.units.UnitType;

class TideGuardian extends Unit {
    public TideGuardian(String id, UnitStats stats) {
        super(id, "Gezeiten-Wächter", Faction.WATER, UnitType.TIDE_GUARDIAN, stats);
    }

    @Override
    public String getSpecialAbility() {
        return "+3 Verteidigung auf Eis-Gelände";
    }
}