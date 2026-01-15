package org.elementarclash.units.state;

import org.elementarclash.units.Unit;

/**
 * Unit is dead. Cannot perform any actions.
 * Terminal state (no transitions out, except Phoenix revival).
 */
public class DeadState implements UnitState {

    private static final DeadState INSTANCE = new DeadState();

    public static DeadState getInstance() {
        return INSTANCE;
    }

    private DeadState() {}

    public boolean hasActionsLeft(Unit unit){
        // always false because dead unit is dead
        return false;
    }

    @Override
    public UnitState transitionToMoving(Unit unit) {
        return this;
    }

    @Override
    public UnitState transitionToAttacking(Unit unit) {
        return this;
    }

    @Override
    public UnitState transitionToIdle(Unit unit) {
        // Special case: Phoenix revival
        // This would be called by Phoenix ability
        return IdleState.getInstance();
    }

    @Override
    public UnitState transitionToStunned(Unit unit, int rounds) {
        return this; // Cannot stun a dead unit
    }

    @Override
    public UnitState transitionToDead(Unit unit) {
        return this; // Already dead
    }

    @Override
    public void onTurnEnd(Unit unit) {
        // No logic for dead units
    }

    @Override
    public String getStateName() {
        return "Dead";
    }
}