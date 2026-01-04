package org.elementarclash.units.decorator;

import org.elementarclash.units.Unit;

/**
 * Temporary buff from abilities (e.g., Feuersturm +3 ATK for 2 turns).
 *
 * Time-limited decorator (expires after N turns).
 */
public class AbilityBuff extends UnitDecorator {

    private final String abilityName;
    private final int attackBonus;
    private final int defenseBonus;
    private int remainingTurns;

    public AbilityBuff(String abilityName, int attackBonus, int defenseBonus, int duration) {
        this.abilityName = abilityName;
        this.attackBonus = attackBonus;
        this.defenseBonus = defenseBonus;
        this.remainingTurns = duration;
    }

    @Override
    public int getAttackBonus(Unit unit) {
        return isExpired() ? 0 : attackBonus;
    }

    @Override
    public int getDefenseBonus(Unit unit) {
        return isExpired() ? 0 : defenseBonus;
    }

    @Override
    public boolean isExpired() {
        return remainingTurns <= 0;
    }

    @Override
    public void tick() {
        if (remainingTurns > 0) {
            remainingTurns--;
        }
    }

    @Override
    public String getDecoratorName() {
        return "AbilityBuff (" + abilityName + ")";
    }

    @Override
    public String getDescription() {
        if (attackBonus > 0 && defenseBonus > 0) {
            return abilityName + ": +" + attackBonus + " ATK, +" + defenseBonus + " DEF (" + remainingTurns + " turns)";
        } else if (attackBonus > 0) {
            return abilityName + ": +" + attackBonus + " ATK (" + remainingTurns + " turns)";
        } else if (defenseBonus > 0) {
            return abilityName + ": +" + defenseBonus + " DEF (" + remainingTurns + " turns)";
        }
        return abilityName;
    }

    public int getRemainingTurns() {
        return remainingTurns;
    }
}