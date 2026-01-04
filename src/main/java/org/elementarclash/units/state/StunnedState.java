package org.elementarclash.units.state;

import org.elementarclash.units.Unit;

/**
 * Unit is stunned (e.g., by Frost Mage ability).
 * Cannot perform any actions for a number of rounds.
 */
public class StunnedState implements UnitState {

    private int remainingRounds;

    public StunnedState(int rounds) {
        this.remainingRounds = rounds;
    }

    @Override
    public boolean canMove(Unit unit) {
        return false;
    }

    @Override
    public boolean canAttack(Unit unit) {
        return false;
    }

    @Override
    public boolean canUseAbility(Unit unit) {
        return false;
    }

    @Override
    public UnitState transitionToMoving(Unit unit) {
        return this; // Cannot move while stunned
    }

    @Override
    public UnitState transitionToAttacking(Unit unit) {
        return this; // Cannot attack while stunned
    }

    @Override
    public UnitState transitionToIdle(Unit unit) {
        if (remainingRounds <= 0) {
            return IdleState.getInstance();
        }
        return this;
    }

    @Override
    public UnitState transitionToStunned(Unit unit, int rounds) {
        // Extend stun duration (doesn't stack, takes max)
        this.remainingRounds = Math.max(this.remainingRounds, rounds);
        return this;
    }

    @Override
    public UnitState transitionToDead(Unit unit) {
        return DeadState.getInstance();
    }

    @Override
    public void onTurnEnd(Unit unit) {
        remainingRounds--;
        if (remainingRounds <= 0) {
            // Transition to idle will happen on next action check
        }
    }

    @Override
    public String getStateName() {
        return "Stunned (" + remainingRounds + " rounds)";
    }

    public int getRemainingRounds() {
        return remainingRounds;
    }
}