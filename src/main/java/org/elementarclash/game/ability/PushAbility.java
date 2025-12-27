package org.elementarclash.game.ability;

import org.elementarclash.battlefield.Battlefield;
import org.elementarclash.game.Game;
import org.elementarclash.game.command.ValidationResult;
import org.elementarclash.units.Unit;
import org.elementarclash.util.Position;

/**
 * Ability that pushes target unit 1 tile away from caster.
 * Used by Storm Caller (Air faction).
 * <p>
 * Design Pattern: Command Pattern (#8)
 * Why: Active abilities follow validate → execute → undo lifecycle.
 * Knockback effect repositions target unit in tactical combat.
 * <p>
 * Effect: Pushes target 1 tile in direction away from caster.
 * Example: Caster at (5,5), target at (6,5) → target pushed to (7,5).
 * <p>
 * Validation: Push direction must be unoccupied and within battlefield bounds.
 * If push target is blocked, ability fails validation.
 */
public class PushAbility implements Ability<PushAbility.UndoState> {

    private static final int RANGE = 1;

    @Override
    public String getName() {
        return "Rückstoß";
    }

    @Override
    public String getDescription() {
        return "Schiebt eine angrenzende Einheit 1 Feld zurück";
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
            return ValidationResult.failure("Kann sich nicht selbst zurückstoßen");
        }

        Position actorPos = actor.getPosition();
        Position targetPos = target.getPosition();

        int distance = actorPos.manhattanDistanceTo(targetPos);
        if (distance > RANGE) {
            return ValidationResult.failure("Ziel muss angrenzend sein");
        }

        Position pushTarget = calculatePushPosition(actorPos, targetPos);

        if (pushTarget.x() < 0 || pushTarget.x() >= Battlefield.GRID_SIZE ||
            pushTarget.y() < 0 || pushTarget.y() >= Battlefield.GRID_SIZE) {
            return ValidationResult.failure("Rückstoß würde Einheit außerhalb des Schlachtfelds befördern");
        }

        if (game.isPositionOccupied(pushTarget)) {
            return ValidationResult.failure("Zielposition ist blockiert");
        }

        return ValidationResult.success();
    }

    @Override
    public UndoState execute(Game game, Unit actor, Object[] targets) {
        Unit target = (Unit) targets[0];
        Position originalPosition = target.getPosition();
        Position pushPosition = calculatePushPosition(actor.getPosition(), originalPosition);

        target.setPosition(pushPosition);

        return new UndoState(target, originalPosition);
    }

    @Override
    public void undo(Game game, Unit actor, UndoState undoState) {
        undoState.target.setPosition(undoState.originalPosition);
    }

    private Position calculatePushPosition(Position from, Position to) {
        int dx = to.x() - from.x();
        int dy = to.y() - from.y();

        int pushX = to.x() + Integer.signum(dx);
        int pushY = to.y() + Integer.signum(dy);

        return new Position(pushX, pushY);
    }

    public record UndoState(Unit target, Position originalPosition) {
    }
}
