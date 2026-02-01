package org.elementarclash.units.bonus.temporary;

import org.elementarclash.units.Unit;
import org.elementarclash.units.bonus.UnitDecorator;

public class HastenedDecorator extends UnitDecorator {
    private static final int MOVEMENT_PENALTY = -1;
    private static final int DURATION = 2;
    private int remainingRounds;

    public HastenedDecorator() {
        this.remainingRounds = DURATION;
    }

    @Override
    public int getMovementBonus(Unit unit) {
        return MOVEMENT_PENALTY;
    }

    @Override
    public int getAttackBonus(Unit unit) {
        return 0;
    }

    @Override
    public int getDefenseBonus(Unit unit) {
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
        return "Hastened";
    }

    @Override
    public String getDescription() {
        return "Movement -1 (" + remainingRounds + " rounds left)";
    }
}