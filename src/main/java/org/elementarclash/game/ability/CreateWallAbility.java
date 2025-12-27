package org.elementarclash.game.ability;

import org.elementarclash.battlefield.Terrain;
import org.elementarclash.game.Game;
import org.elementarclash.game.command.ValidationResult;
import org.elementarclash.units.Unit;
import org.elementarclash.util.Position;

/**
 * Active ability to create an earth wall at an adjacent position.
 * Used by Terra Shaman (Earth faction).
 * <p>
 * Design Pattern: Command Pattern (#8) + Template Method
 * Why: Active abilities follow the validate → execute → undo lifecycle.
 * Creates a STONE terrain wall that blocks movement and provides defense bonus.
 */
public class CreateWallAbility implements Ability<CreateWallAbility.UndoState> {

    private static final int COOLDOWN = 3;
    private static final int RANGE = 1;

    @Override
    public String getName() {
        return "Erdmauer erschaffen";
    }

    @Override
    public String getDescription() {
        return "Erschafft eine Steinmauer auf einem angrenzenden Feld (Abklingzeit: 3 Runden)";
    }

    @Override
    public ValidationResult validate(Game game, Unit actor, Object[] targets) {
        if (targets == null || targets.length == 0) {
            return ValidationResult.failure("Keine Zielposition angegeben");
        }

        if (!(targets[0] instanceof Position)) {
            return ValidationResult.failure("Ungültige Zielposition");
        }

        Position wallPosition = (Position) targets[0];

        if (!wallPosition.isAdjacentTo(actor.getPosition())) {
            return ValidationResult.failure("Mauer muss auf einem angrenzenden Feld erschaffen werden");
        }

        if (game.isPositionOccupied(wallPosition)) {
            return ValidationResult.failure("Position ist durch eine Einheit belegt");
        }

        return ValidationResult.success();
    }

    @Override
    public UndoState execute(Game game, Unit actor, Object[] targets) {
        Position wallPosition = (Position) targets[0];
        Terrain previousTerrain = game.getTerrainAt(wallPosition);

        game.getBattlefield().getCell(wallPosition.x(), wallPosition.y())
            .setTerrain(Terrain.STONE);

        return new UndoState(wallPosition, previousTerrain);
    }

    @Override
    public void undo(Game game, Unit actor, UndoState undoState) {
        game.getBattlefield().getCell(undoState.position.x(), undoState.position.y())
            .setTerrain(undoState.previousTerrain);
    }

    @Override
    public int getCooldown() {
        return COOLDOWN;
    }

    public record UndoState(Position position, Terrain previousTerrain) {
    }
}
