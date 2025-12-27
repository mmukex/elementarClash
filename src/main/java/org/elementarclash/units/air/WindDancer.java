package org.elementarclash.units.air;

import org.elementarclash.faction.Faction;
import org.elementarclash.units.Unit;
import org.elementarclash.units.UnitStats;
import org.elementarclash.units.UnitType;
import org.elementarclash.units.strategy.movement.FlyingMovementStrategy;
import org.elementarclash.units.strategy.attack.MeleeAttackStrategy;

/**
 * Air faction's mobile striker with conditional double attack.
 * <p>
 * Faction: Air | Movement: Flying | Attack: Melee (range: 1)
 * <p>
 * Special: Double attack after moving ≥3 tiles. Avoids opportunity attacks.
 * <p>
 * Tactical: Hit-and-run specialist. Uses mobility for repeated strikes.
 */
class WindDancer extends Unit {
    private static final int DOUBLE_ATTACK_MOVEMENT_THRESHOLD = 3;

    public WindDancer(String id, UnitStats stats) {
        super(id, "Wind-Tänzer", Faction.AIR, UnitType.WIND_DANCER, stats);
        setMovementStrategy(new FlyingMovementStrategy());
        setAttackStrategy(new MeleeAttackStrategy());
    }

    @Override
    public String getSpecialAbility() {
        return "Kann 2× angreifen nach Bewegung ≥3 Felder";
    }

    public boolean canDoubleAttack(int tilesMovedThisTurn) {
        return tilesMovedThisTurn >= DOUBLE_ATTACK_MOVEMENT_THRESHOLD;
    }

    public int getDoubleAttackThreshold() {
        return DOUBLE_ATTACK_MOVEMENT_THRESHOLD;
    }
}
