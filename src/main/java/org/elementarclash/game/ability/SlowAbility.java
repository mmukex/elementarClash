package org.elementarclash.game.ability;

import org.elementarclash.game.Game;
import org.elementarclash.game.command.ValidationResult;
import org.elementarclash.units.Unit;
import org.elementarclash.units.strategy.movement.MovementStrategy;
import org.elementarclash.units.strategy.movement.SlowedMovementStrategy;

/**
 * Ability that slows target unit's movement for 2 turns.
 * Used by Frost Mage (Water faction).
 * <p>
 * Design Pattern: Command Pattern (#8) + Strategy Pattern (#5)
 * Why: Active abilities follow validate → execute → undo lifecycle.
 * Modifies target's movement by replacing MovementStrategy with SlowedMovementStrategy.
 * <p>
 * Effect: Reduces target movement range by 1 for 2 turns.
 * Example: Unit with 3 movement → 2 movement when slowed.
 * <p>
 * Integration Note: Duration tracking (2 turns) requires State Pattern (#6 @crstmkt).
 * Currently just applies slow effect - @crstmkt will add turn-based removal.
 */
public class SlowAbility implements Ability<SlowAbility.UndoState> {

    private static final int RANGE = 3;

    @Override
    public String getName() {
        return "Frost-Verlangsamung";
    }

    @Override
    public String getDescription() {
        return "Verlangsamt die Zieleinheit (-1 Bewegung für 2 Runden, Reichweite: " + RANGE + ")";
    }

    @Override
    public ValidationResult validate(Game game, Unit actor, Object[] targets) {
        if (targets == null || targets.length == 0) {
            return ValidationResult.failure("Keine Zieleinheit angegeben");
        }

        if (!(targets[0] instanceof Unit)) {
            return ValidationResult.failure("Ungültiges Ziel");
        }

        Unit target = (Unit) targets[0];

        if (!target.isAlive()) {
            return ValidationResult.failure("Ziel ist bereits besiegt");
        }

        if (target == actor) {
            return ValidationResult.failure("Kann sich nicht selbst verlangsamen");
        }

        int distance = actor.getPosition().manhattanDistanceTo(target.getPosition());
        if (distance > RANGE) {
            return ValidationResult.failure("Ziel außerhalb der Reichweite (" + RANGE + ")");
        }

        if (target.getMovementStrategy() instanceof SlowedMovementStrategy) {
            return ValidationResult.failure("Ziel ist bereits verlangsamt");
        }

        return ValidationResult.success();
    }

    @Override
    public UndoState execute(Game game, Unit actor, Object[] targets) {
        Unit target = (Unit) targets[0];
        MovementStrategy originalStrategy = target.getMovementStrategy();

        SlowedMovementStrategy slowedStrategy = new SlowedMovementStrategy(originalStrategy);
        target.setMovementStrategy(slowedStrategy);

        return new UndoState(target, originalStrategy);
    }

    @Override
    public void undo(Game game, Unit actor, UndoState undoState) {
        undoState.target.setMovementStrategy(undoState.originalStrategy);
    }

    public record UndoState(Unit target, MovementStrategy originalStrategy) {
    }
}
