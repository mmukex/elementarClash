package org.elementarclash.units.state;

import org.elementarclash.units.Unit;

/**
 * State Pattern (GoF #8) - Unit State Machine
 *
 * Manages unit states: Idle, Moving, Attacking, Stunned, Dead
 *
 * Why State Pattern?
 * - Units can only perform certain actions in certain states
 * - Clear state transitions prevent invalid actions
 * - Avoids complex if-else chains checking multiple boolean flags
 *
 * @author @crstmkt
 */
public interface UnitState {

    /**
     * Can this unit move in the current state?
     */
    boolean canMove(Unit unit);

    /**
     * Can this unit attack in the current state?
     */
    boolean canAttack(Unit unit);

    /**
     * Can this unit use abilities in the current state?
     */
    boolean canUseAbility(Unit unit);

    /**
     * Transition to Moving state.
     * @return new state or this if transition not allowed
     */
    UnitState transitionToMoving(Unit unit);

    /**
     * Transition to Attacking state.
     * @return new state or this if transition not allowed
     */
    UnitState transitionToAttacking(Unit unit);

    /**
     * Transition to Idle state (after action completed).
     * @return new state
     */
    UnitState transitionToIdle(Unit unit);

    /**
     * Transition to Stunned state (e.g., from Frost Mage ability).
     * @param rounds number of rounds stunned
     * @return StunnedState
     */
    UnitState transitionToStunned(Unit unit, int rounds);

    /**
     * Transition to Dead state.
     * @return DeadState
     */
    UnitState transitionToDead(Unit unit);

    /**
     * Called at the end of each turn.
     * Handles state-specific turn logic (e.g., stun countdown).
     */
    void onTurnEnd(Unit unit);

    /**
     * Get state name for debugging/logging.
     */
    String getStateName();
}