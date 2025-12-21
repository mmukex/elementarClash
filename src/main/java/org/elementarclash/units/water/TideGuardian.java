package org.elementarclash.units.water;

import org.elementarclash.faction.Faction;
import org.elementarclash.units.Unit;
import org.elementarclash.units.UnitStats;
import org.elementarclash.units.UnitType;
import org.elementarclash.units.strategy.movement.GroundMovementStrategy;
import org.elementarclash.units.strategy.attack.MeleeAttackStrategy;

class TideGuardian extends Unit {
    public TideGuardian(String id, UnitStats stats) {
        super(id, "Gezeiten-Wächter", Faction.WATER, UnitType.TIDE_GUARDIAN, stats);
        setMovementStrategy(new GroundMovementStrategy(Faction.WATER));
        setAttackStrategy(new MeleeAttackStrategy());
    }

    @Override
    public String getSpecialAbility() {
        return "+3 Verteidigung auf Eis-Gelände";
    }
}