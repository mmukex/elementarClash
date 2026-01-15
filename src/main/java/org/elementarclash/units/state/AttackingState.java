package org.elementarclash.units.state;

import org.elementarclash.units.Unit;

/**
 * Unit is currently attacking or has attacked this turn.
 * Cannot move or attack again.
 */
public class AttackingState implements UnitState {

    private static final AttackingState INSTANCE = new AttackingState();

    public static AttackingState getInstance() {
        return INSTANCE;
    }

    private AttackingState() {}

    public boolean hasActionsLeft(Unit unit){
        return unit.getActionsThisTurn() < unit.getMaxActionsPerTurn();
    }

    @Override
    public UnitState transitionToMoving(Unit unit) {
        return this; // Cannot move after attacking
    }

    @Override
    public UnitState transitionToAttacking(Unit unit) {
        return this; // Already attacked
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
        return "Attacking";
    }
}