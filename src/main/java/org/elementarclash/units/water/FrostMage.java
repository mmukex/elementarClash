package org.elementarclash.units.water;

import org.elementarclash.faction.Faction;
import org.elementarclash.units.Unit;
import org.elementarclash.units.UnitStats;
import org.elementarclash.units.UnitType;

public class FrostMage extends Unit {
    public FrostMage(String id, UnitStats stats) {
        super(id, "Frost-Magier", Faction.WATER, UnitType.FROST_MAGE, stats);
    }

    @Override
    public String getSpecialAbility() {
        return "Angriffe verlangsamen Gegner (-1 Bewegung für 2 Runden)";
    }

    public boolean appliesSlowEffect() {
        return true;
    }
}