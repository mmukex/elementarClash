package org.elementarclash.units.types.fire;

import org.elementarclash.units.Faction;
import org.elementarclash.units.Unit;
import org.elementarclash.units.UnitStats;
import org.elementarclash.units.UnitType;
import org.elementarclash.units.strategy.movement.FlyingMovementStrategy;
import org.elementarclash.units.strategy.attack.MeleeAttackStrategy;

/**
 * Fire faction's mythical flying unit with resurrection ability.
 * <p>
 * Faction: Fire
 * <p>
 * Movement: Flying (ignores terrain penalties)
 * Attack: Melee (range: 1)
 * <p>
 * Special Ability: Resurrects once at 50% HP when killed.
 * The phoenix returns from ashes after its first death, but subsequent deaths are permanent.
 * <p>
 * Tactical Use: High-risk scout and flanker.
 * Flying mobility allows bypassing terrain obstacles. Resurrection provides second chance in combat.
 * Vulnerable to ranged attacks and area damage.
 */
class Phoenix extends Unit {

    private static final double RESURRECTION_HEALTH_PERCENT = 0.5;

    private boolean hasResurrected = false;

    public Phoenix(String id, UnitStats stats) {
        super(id, "Phönix", Faction.FIRE, UnitType.PHOENIX, stats);
        setMovementStrategy(new FlyingMovementStrategy());
        setAttackStrategy(new MeleeAttackStrategy());
    }

    @Override
    public void takeDamage(int damage) {
        super.takeDamage(damage);

        if (!isAlive() && !hasResurrected) {
            int resurrectionHealth = (int) (getBaseStats().maxHealth() * RESURRECTION_HEALTH_PERCENT);
            setCurrentHealth(resurrectionHealth);
            hasResurrected = true;
        }
    }

    @Override
    public String getDescription() {
        return "Fliegend, Wiederbelebung 1× (50% LP)";
    }
}
