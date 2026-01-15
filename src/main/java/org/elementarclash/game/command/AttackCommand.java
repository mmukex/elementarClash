package org.elementarclash.game.command;

import lombok.Getter;
import org.elementarclash.game.Game;
import org.elementarclash.game.combat.DamageCalculator;
import org.elementarclash.game.combat.DamageResult;
import org.elementarclash.game.event.UnitAttackedEvent;
import org.elementarclash.units.Unit;
import org.elementarclash.units.state.DeadState;
import org.elementarclash.units.state.StunnedState;
import org.elementarclash.units.state.UnitState;

/**
 * Command for attacking a target unit.
 * Validates attack using unit's AttackStrategy.
 * Stores previous health and damage dealt for undo.
 * <p>
 * Design Pattern: Command (GoF #4)
 * Why: Encapsulates attack action with validation and undo capability.
 * <p>
 * Validation:
 * 1. Attacker and target exist in game
 * 2. Attacker is alive
 * 3. Target is alive
 * 4. Attacker hasn't attacked this turn
 * 5. Attack is valid (delegates to Strategy)
 * <p>
 * Undo: Restores target health, clears attacked flag, resurrects target if needed
 *
 * @author mmukex
 */
@Getter
public class AttackCommand implements Command {

    private final Unit actor;
    private final Unit target;

    private int targetPreviousHealth;
    private int damageDealt;
    private boolean targetWasAlive;
    private boolean wasExecuted;

    public AttackCommand(Unit attacker, Unit target) {
        this.actor = attacker;
        this.target = target;
    }

    @Override
    public ValidationResult validate(Game game) {
        ValidationResult actorCheck = validateActorExists(game, actor);
        if (!actorCheck.isValid()) {
            return actorCheck;
        }

        actorCheck = validateActorAlive(actor);
        if (!actorCheck.isValid()) {
            return actorCheck;
        }

        ValidationResult targetCheck = validateTargetExists(game, target);
        if (!targetCheck.isValid()) {
            return targetCheck;
        }

        if (actor.getCurrentState() instanceof StunnedState || actor.getCurrentState() instanceof DeadState) {
            return ValidationResult.failure(
                    actor.getName() + " cannot attack (State: " + actor.getCurrentState().getStateName() + ")"
            );
        }

        if (!actor.hasActionsLeft()) {
            return ValidationResult.failure(
                    String.format("%s has already attacked this turn", actor.getName())
            );
        }

        if (!game.canAttack(actor, target)) {
            return ValidationResult.failure(
                    String.format("Invalid attack on %s (range/line of sight)", target.getName())
            );
        }

        return ValidationResult.success();
    }

    @Override
    public void execute(Game game) {
        this.targetPreviousHealth = target.getCurrentHealth();
        this.targetWasAlive = target.isAlive();

        // TODO: crstmkt - Chain of Responsibility - Configure damage modifier chain here -> Wird doch im DamageCalculator gemacht?
        DamageCalculator calculator = new DamageCalculator();
        DamageResult result = calculator.calculateDamage(actor, target, game);

        this.damageDealt = result.totalDamage();
        target.takeDamage(result.totalDamage());
        actor.incrementActionsThisTurn();
        //actor.markAttackedThisTurn();
        actor.startAttacking();

        game.notifyObservers(new UnitAttackedEvent(actor, target, result));

        if (!target.isAlive()) {
            game.handleUnitDeath(target);
        }

        this.wasExecuted = true;
    }

    @Override
    public void undo(Game game) {
        if (!wasExecuted) {
            throw new IllegalStateException("Cannot undo command that wasn't executed");
        }

        int healthToRestore = targetPreviousHealth - target.getCurrentHealth();
        if (healthToRestore > 0) {
            target.heal(healthToRestore);
        }

        actor.decrementActionsThisTurn();

        // Future: Undo death effects when implemented
    }

    @Override
    public CommandType getType() {
        return CommandType.ATTACK;
    }

    @Override
    public String toString() {
        return String.format("AttackCommand[%s attacks %s for %d damage]",
                actor.getName(), target.getName(), damageDealt);
    }
}
