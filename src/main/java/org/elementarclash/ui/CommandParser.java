package org.elementarclash.ui;

import org.elementarclash.faction.Faction;
import org.elementarclash.game.Game;
import org.elementarclash.game.ability.CreateWallAbility;
import org.elementarclash.game.command.AttackCommand;
import org.elementarclash.game.command.MoveCommand;
import org.elementarclash.game.command.UseAbilityCommand;
import org.elementarclash.units.Unit;
import org.elementarclash.units.UnitType;
import org.elementarclash.util.Position;

import java.util.List;

/**
 * Parses user input strings into Command objects.
 * Single Responsibility: String parsing and Command creation.
 */
public class CommandParser {

    private final ConsoleUI ui;

    public CommandParser(ConsoleUI ui) {
        this.ui = ui;
    }

    public MoveCommand parseMove(Game game) {
        try {
            String unitId = ui.promptUnitSelection("Welche Einheit bewegen? (z.B. F1): ");
            Unit unit = findUnitById(game, unitId);

            if (unit == null) {
                ui.showError("Einheit nicht gefunden: " + unitId);
                return null;
            }

            if (unit.getFaction() != game.getActiveFaction()) {
                ui.showError("Diese Einheit gehört nicht zur aktiven Fraktion");
                return null;
            }

            String posInput = ui.promptPosition("Zielposition (z.B. 5,3): ");
            Position target = parsePosition(posInput);

            if (target == null) {
                ui.showError("Ungültige Position: " + posInput);
                return null;
            }

            return new MoveCommand(unit, target);

        } catch (Exception e) {
            ui.showError("Eingabefehler: " + e.getMessage());
            return null;
        }
    }

    public AttackCommand parseAttack(Game game) {
        try {
            String attackerId = ui.promptUnitSelection("Welche Einheit angreift? (z.B. F1): ");
            Unit attacker = findUnitById(game, attackerId);

            if (attacker == null) {
                ui.showError("Einheit nicht gefunden: " + attackerId);
                return null;
            }

            if (attacker.getFaction() != game.getActiveFaction()) {
                ui.showError("Diese Einheit gehört nicht zur aktiven Fraktion");
                return null;
            }

            String targetId = ui.promptUnitSelection("Welches Ziel? (z.B. W2): ");
            Unit target = findUnitById(game, targetId);

            if (target == null) {
                ui.showError("Ziel nicht gefunden: " + targetId);
                return null;
            }

            return new AttackCommand(attacker, target);

        } catch (Exception e) {
            ui.showError("Eingabefehler: " + e.getMessage());
            return null;
        }
    }

    public UseAbilityCommand parseAbility(Game game) {
        try {
            String unitId = ui.promptUnitSelection("Welche Einheit nutzt Fähigkeit? (z.B. E1): ");
            Unit actor = findUnitById(game, unitId);

            if (actor == null) {
                ui.showError("Einheit nicht gefunden: " + unitId);
                return null;
            }

            if (actor.getFaction() != game.getActiveFaction()) {
                ui.showError("Diese Einheit gehört nicht zur aktiven Fraktion");
                return null;
            }

            if (actor.getType() == UnitType.TERRA_SHAMAN) {
                String posInput = ui.promptPosition("Wo soll die Erdmauer erschaffen werden? (z.B. 3,4): ");
                Position wallPosition = parsePosition(posInput);

                if (wallPosition == null) {
                    ui.showError("Ungültige Position: " + posInput);
                    return null;
                }

                CreateWallAbility ability = new CreateWallAbility();
                return new UseAbilityCommand<>(actor, ability, wallPosition);
            }

            ui.showError("Diese Einheit hat keine Fähigkeiten");
            return null;

        } catch (Exception e) {
            ui.showError("Eingabefehler: " + e.getMessage());
            return null;
        }
    }

    private Unit findUnitById(Game game, String unitId) {
        if (unitId == null || unitId.length() < 2) {
            return null;
        }

        char factionChar = unitId.charAt(0);
        String numberPart = unitId.substring(1);

        Faction faction = parseFaction(factionChar);
        if (faction == null) {
            return null;
        }

        int unitIndex;
        try {
            unitIndex = Integer.parseInt(numberPart) - 1;
        } catch (NumberFormatException e) {
            return null;
        }

        List<Unit> units = game.getUnitsOfFaction(faction);
        if (unitIndex < 0 || unitIndex >= units.size()) {
            return null;
        }

        return units.get(unitIndex);
    }

    private Position parsePosition(String input) {
        if (input == null || input.isEmpty()) {
            return null;
        }

        String[] parts = input.split(",");
        if (parts.length != 2) {
            return null;
        }

        try {
            int x = Integer.parseInt(parts[0].trim());
            int y = Integer.parseInt(parts[1].trim());
            return new Position(x, y);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Faction parseFaction(char c) {
        return switch (c) {
            case 'F' -> Faction.FIRE;
            case 'W' -> Faction.WATER;
            case 'E' -> Faction.EARTH;
            case 'A' -> Faction.AIR;
            default -> null;
        };
    }
}
