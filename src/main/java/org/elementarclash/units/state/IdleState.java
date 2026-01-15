package org.elementarclash.units.state;

import org.elementarclash.units.Unit;

/**
 * Unit is idle and ready to act.
 * Can move, attack, or use abilities.
 */
public class IdleState implements UnitState {

    private static final IdleState INSTANCE = new IdleState();

    public static IdleState getInstance() {
        return INSTANCE;
    }

    private IdleState() {}

    public boolean hasActionsLeft(Unit unit){
        return unit.getActionsThisTurn() < unit.getMaxActionsPerTurn();
    }

    @Override
    public UnitState transitionToMoving(Unit unit) {
        if (hasActionsLeft(unit)) {
            return MovingState.getInstance();
        }
        return this;
    }

    @Override
    public UnitState transitionToAttacking(Unit unit) {
        if (hasActionsLeft(unit)) {
            return AttackingState.getInstance();
        }
        return this;
    }

    @Override
    public UnitState transitionToIdle(Unit unit) {
        return this; // Already idle
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
        // No special logic for idle state
    }

    @Override
    public String getStateName() {
        return "Idle";
    }
}