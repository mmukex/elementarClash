package org.elementarclash.units.state;

import org.elementarclash.units.Unit;

/**
 * Unit is currently moving.
 * Can still attack or use abilities after moving.
 */
public class MovingState implements UnitState {

    private static final MovingState INSTANCE = new MovingState();

    public static MovingState getInstance() {
        return INSTANCE;
    }

    private MovingState() {}

    @Override
    public boolean canMove(Unit unit) {
        return false; // Already moved this turn
    }

    @Override
    public boolean canAttack(Unit unit) {
        return !unit.hasAttackedThisTurn();
    }

    @Override
    public boolean canUseAbility(Unit unit) {
        return true;
    }

    @Override
    public UnitState transitionToMoving(Unit unit) {
        return this; // Already moving
    }

    @Override
    public UnitState transitionToAttacking(Unit unit) {
        if (canAttack(unit)) {
            return AttackingState.getInstance();
        }
        return this;
    }

    @Override
    public UnitState transitionToIdle(Unit unit) {
        return IdleState.getInstance();
    }

    @Override
    public UnitState transitionToStunned(Unit unit, int rounds) {
        return new StunnedState(rounds);
    }

    @Override
    public UnitState transitionToDead(Unit unit) {
        return DeadState.getInstance();
    }

    @Override
    public void onTurnEnd(Unit unit) {
        // Transition back to idle at end of turn
    }

    @Override
    public String getStateName() {
        return "Moving";
    }
}