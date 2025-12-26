package org.elementarclash.ui;

import org.elementarclash.battlefield.Battlefield;
import org.elementarclash.battlefield.Terrain;
import org.elementarclash.game.Game;
import org.elementarclash.units.Unit;
import org.elementarclash.util.Position;

/**
 * Renders game state as ASCII text for console output.
 */
public class ConsoleGameRenderer implements GameRenderer {

    @Override
    public String render(Game game) {
        StringBuilder sb = new StringBuilder();
        renderHeader(sb, game);
        renderGrid(sb, game);
        renderLegend(sb);
        renderUnitSummary(sb, game);
        return sb.toString();
    }

    private void renderHeader(StringBuilder sb, Game game) {
        sb.append("################################################################################################").append(System.lineSeparator());
        sb.append("               ELEMENTARCLASH - Runde ").append(game.getTurnNumber()).append(System.lineSeparator());
        sb.append("################################################################################################").append(System.lineSeparator());

        if (game.getActiveFaction() != null) {
            sb.append("Aktive Fraktion: ").append(game.getActiveFaction().getGermanName())
                    .append(" ").append(game.getActiveFaction().getIcon()).append(System.lineSeparator());
        }

        sb.append("Status: ").append(game.getStatus()).append(System.lineSeparator());
    }

    private void renderGrid(StringBuilder sb, Game game) {
        renderGridHeader(sb);
        renderGridRows(sb, game);
    }

    private void renderGridHeader(StringBuilder sb) {
        sb.append("   ");
        for (int x = 0; x < Battlefield.GRID_SIZE; x++) {
            sb.append("  ").append(x).append("  ");
        }
        sb.append(System.lineSeparator());
    }

    private void renderGridRows(StringBuilder sb, Game game) {
        for (int y = 0; y < Battlefield.GRID_SIZE; y++) {
            sb.append(y).append(" |");
            for (int x = 0; x < Battlefield.GRID_SIZE; x++) {
                Position pos = new Position(x, y);
                renderCell(sb, game, pos);
            }
            sb.append(System.lineSeparator()).append(System.lineSeparator());
        }
    }

    private void renderCell(StringBuilder sb, Game game, Position pos) {
        Unit unit = game.getUnitAt(pos);

        if (unit != null && unit.isAlive()) {
            sb.append(" ").append(unit.getFaction().getIcon()).append(" |");
        } else {
            sb.append(" ").append(game.getBattlefield().getTerrainAt(pos).getIcon()).append(" |");
        }
    }

    private void renderLegend(StringBuilder sb) {
        sb.append(System.lineSeparator()).append("Legende:");
        for (Terrain terrain : Terrain.values()) {
            sb.append("  ").append(terrain.getIcon()).append(" ")
                    .append(terrain.getGermanName()).append(" | ");
        }
        sb.append(System.lineSeparator()).append(System.lineSeparator());
    }

    private void renderUnitSummary(StringBuilder sb, Game game) {
        long livingUnits = game.getUnits().stream().filter(Unit::isAlive).count();
        sb.append("Lebende Einheiten: ").append(livingUnits)
                .append("/").append(game.getUnits().size()).append(System.lineSeparator());
    }
}
