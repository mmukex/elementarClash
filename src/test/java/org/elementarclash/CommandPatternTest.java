package org.elementarclash;

import org.elementarclash.battlefield.Terrain;
import org.elementarclash.game.Game;
import org.elementarclash.game.GameBuilder;
import org.elementarclash.game.command.*;
import org.elementarclash.units.Faction;
import org.elementarclash.units.Unit;
import org.elementarclash.units.UnitFactory;
import org.elementarclash.units.UnitType;
import org.elementarclash.units.types.fire.FireUnitFactory;
import org.elementarclash.units.types.water.WaterUnitFactory;
import org.elementarclash.util.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CommandPatternTest {

    private Game game;
    private Unit fireUnit;
    private Unit waterUnit;
    private UnitFactory fireFactory;
    private UnitFactory waterFactory;

    @BeforeEach
    void setUp() {
        fireFactory = new FireUnitFactory();
        waterFactory = new WaterUnitFactory();

        fireUnit = fireFactory.createUnit(UnitType.INFERNO_WARRIOR);
        waterUnit = waterFactory.createUnit(UnitType.TIDE_GUARDIAN);

        GameBuilder builder = new GameBuilder()
                .withFactions(Faction.FIRE, Faction.WATER)
                .withRandomSeed(1L)
                .withCustomTerrain(Map.of(
                        Terrain.DESERT, 100,
                        Terrain.LAVA, 0, Terrain.ICE, 0,
                        Terrain.FOREST, 0, Terrain.STONE, 0
                ));
        builder.addUnit(fireUnit, Faction.FIRE);
        builder.addUnit(waterUnit, Faction.WATER);
        game = builder.build();
        game.startGame();
    }

    @Test
    void moveCommandImplementsCommandInterface() {
        MoveCommand cmd = new MoveCommand(fireUnit, new Position(5, 5));
        assertInstanceOf(Command.class, cmd);
    }

    @Test
    void attackCommandImplementsCommandInterface() {
        AttackCommand cmd = new AttackCommand(fireUnit, waterUnit);
        assertInstanceOf(Command.class, cmd);
    }

    @Test
    void moveCommandTypeIsMOVE() {
        MoveCommand cmd = new MoveCommand(fireUnit, new Position(5, 5));
        assertEquals(CommandType.MOVE, cmd.getType());
    }

    @Test
    void attackCommandTypeIsATTACK() {
        AttackCommand cmd = new AttackCommand(fireUnit, waterUnit);
        assertEquals(CommandType.ATTACK, cmd.getType());
    }

    @Test
    void moveCommandExecuteChangesUnitPosition() {
        Position originalPos = fireUnit.getPosition();
        Position adjacentPos = findAdjacentEmptyPosition(originalPos);
        assertNotNull(adjacentPos);

        MoveCommand cmd = new MoveCommand(fireUnit, adjacentPos);
        ValidationResult result = game.executeCommand(cmd);

        assertTrue(result.isValid());
        assertEquals(adjacentPos, fireUnit.getPosition());
        assertNotEquals(originalPos, fireUnit.getPosition());
    }

    @Test
    void moveCommandUndoRestoresOriginalPosition() {
        Position originalPos = fireUnit.getPosition();
        Position adjacentPos = findAdjacentEmptyPosition(originalPos);
        assertNotNull(adjacentPos);

        MoveCommand cmd = new MoveCommand(fireUnit, adjacentPos);
        ValidationResult result = game.executeCommand(cmd);

        assertTrue(result.isValid());
        game.undoLastCommand();
        assertEquals(originalPos, fireUnit.getPosition());
    }

    @Test
    void attackCommandExecuteReducesTargetHealth() {
        placeUnitsAdjacent();

        int healthBefore = waterUnit.getCurrentHealth();
        AttackCommand cmd = new AttackCommand(fireUnit, waterUnit);
        ValidationResult result = game.executeCommand(cmd);

        assertTrue(result.isValid());
        assertTrue(waterUnit.getCurrentHealth() < healthBefore);
    }

    @Test
    void attackCommandUndoRestoresTargetHealth() {
        placeUnitsAdjacent();

        int healthBefore = waterUnit.getCurrentHealth();
        AttackCommand cmd = new AttackCommand(fireUnit, waterUnit);
        ValidationResult result = game.executeCommand(cmd);

        assertTrue(result.isValid());
        game.undoLastCommand();
        assertEquals(healthBefore, waterUnit.getCurrentHealth());
    }

    @Test
    void commandExecutorRedoRepeatsUndoneCommand() {
        Position originalPos = fireUnit.getPosition();
        Position adjacentPos = findAdjacentEmptyPosition(originalPos);
        assertNotNull(adjacentPos);

        MoveCommand cmd = new MoveCommand(fireUnit, adjacentPos);
        ValidationResult result = game.executeCommand(cmd);
        assertTrue(result.isValid());

        game.undoLastCommand();
        assertEquals(originalPos, fireUnit.getPosition());

        game.redoLastCommand();
        assertEquals(adjacentPos, fireUnit.getPosition());
    }

    @Test
    void undoReturnsFalseWhenNoHistory() {
        boolean result = game.undoLastCommand();
        assertFalse(result);
    }

    @Test
    void redoReturnsFalseWhenNoUndoneCommands() {
        boolean result = game.redoLastCommand();
        assertFalse(result);
    }

    @Test
    void validationResultSuccessIsValid() {
        ValidationResult success = ValidationResult.success();
        assertTrue(success.isValid());
    }

    @Test
    void validationResultFailureIsNotValid() {
        ValidationResult failure = ValidationResult.failure("Error");
        assertFalse(failure.isValid());
        assertNotNull(failure.errorMessage());
    }

    @Test
    void moveCommandValidationFailsForOccupiedPosition() {
        Position targetPos = waterUnit.getPosition();
        MoveCommand cmd = new MoveCommand(fireUnit, targetPos);
        ValidationResult result = cmd.validate(game);
        assertFalse(result.isValid());
    }

    @Test
    void commandHistoryPushAndPopForUndo() {
        CommandHistory history = new CommandHistory();
        MoveCommand cmd = new MoveCommand(fireUnit, new Position(5, 5));

        history.push(cmd);
        Command popped = history.popForUndo();

        assertSame(cmd, popped);
    }

    @Test
    void commandHistoryPopForRedoAfterUndo() {
        CommandHistory history = new CommandHistory();
        MoveCommand cmd = new MoveCommand(fireUnit, new Position(5, 5));

        history.push(cmd);
        history.popForUndo();
        Command redone = history.popForRedo();

        assertSame(cmd, redone);
    }

    @Test
    void commandHistoryClearRemovesAllCommands() {
        CommandHistory history = new CommandHistory();
        history.push(new MoveCommand(fireUnit, new Position(5, 5)));
        history.push(new MoveCommand(fireUnit, new Position(6, 6)));

        history.clear();

        assertNull(history.popForUndo());
        assertNull(history.popForRedo());
    }

    @Test
    void newCommandClearsRedoStack() {
        CommandHistory history = new CommandHistory();
        MoveCommand cmd1 = new MoveCommand(fireUnit, new Position(5, 5));
        MoveCommand cmd2 = new MoveCommand(fireUnit, new Position(6, 6));

        history.push(cmd1);
        history.popForUndo();
        history.push(cmd2);

        assertNull(history.popForRedo());
    }

    private void placeUnitsAdjacent() {
        Position firePos = fireUnit.getPosition();
        int nx = Math.min(firePos.x() + 1, 9);
        int ny = firePos.y();
        Position adjacentPos = new Position(nx, ny);

        if (!adjacentPos.equals(waterUnit.getPosition())) {
            game.moveUnitInternal(waterUnit, adjacentPos);
        }
    }

    private Position findAdjacentEmptyPosition(Position pos) {
        int[][] deltas = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        for (int[] d : deltas) {
            int nx = pos.x() + d[0];
            int ny = pos.y() + d[1];
            if (nx >= 0 && nx <= 9 && ny >= 0 && ny <= 9) {
                Position candidate = new Position(nx, ny);
                if (!game.isPositionOccupied(candidate)) {
                    return candidate;
                }
            }
        }
        return null;
    }
}
