package org.elementarclash.units.water;

import org.elementarclash.faction.Faction;
import org.elementarclash.units.Unit;
import org.elementarclash.units.UnitStats;
import org.elementarclash.units.UnitType;

public class WaveRider extends Unit {
    public WaveRider(String id, UnitStats stats) {
        super(id, "Wellen-Reiter", Faction.WATER, UnitType.WAVE_RIDER, stats);
    }

    @Override
    public String getSpecialAbility() {
        return "Kann durch Wasser/Eis mit Kosten 1 ziehen";
    }
}