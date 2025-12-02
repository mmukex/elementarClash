package units.impl.fire;

import faction.Faction;
import units.Unit;
import units.UnitStats;
import units.UnitType;

/**
 * Phoenix - Fire faction flying unit.
 * Special: Flying (ignores terrain), one-time resurrection at 50% HP.
 */
public class Phoenix extends Unit {
    private boolean hasResurrected = false;

    public Phoenix(String id, UnitStats stats) {
        super(id, "Phönix", Faction.FIRE, UnitType.PHOENIX, stats);
    }

    @Override
    public String getSpecialAbility() {
        return "Fliegend, Wiederbelebung 1× (50% LP)";
    }

    /**
     * Special mechanic: Phoenix resurrects once when killed.
     * This will be triggered by the State pattern (death state).
     */
    public boolean canResurrect() {
        return !hasResurrected && !isAlive();
    }

    public void resurrect() {
        if (canResurrect()) {
            int resurrectionHealth = getBaseStats().maxHealth() / 2;
            heal(resurrectionHealth);
            hasResurrected = true;
        }
    }

    /**
     * Flying units ignore terrain movement penalties.
     */
    public boolean isFlying() {
        return true;
    }
}
