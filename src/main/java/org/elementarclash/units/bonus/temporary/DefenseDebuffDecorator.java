package org.elementarclash.units.bonus.temporary;

import org.elementarclash.units.Unit;
import org.elementarclash.units.bonus.UnitDecorator;

public class DefenseDebuffDecorator extends UnitDecorator {
    private static final int BONUS = -2;
    private static final int DURATION = 2;
    private int remainingRounds;

    public DefenseDebuffDecorator() {
        this.remainingRounds = DURATION;
    }

    @Override
    public int getAttackBonus(Unit unit) { return 0;}

    @Override
    public int getDefenseBonus(Unit unit) {
        return BONUS;
    }

    @Override
    public int getMovementBonus(Unit unit) {
        return 0;
    }

    @Override
    public boolean isExpired() {
        return remainingRounds <= 0;
    }

    @Override
    public void tick() {
        remainingRounds--;
    }

    @Override
    public String getDecoratorName() {
        return "Defense Debuff";
    }

    @Override
    public String getDescription() {
        return "-2 Defense (" + remainingRounds + " rounds left)";
    }
}