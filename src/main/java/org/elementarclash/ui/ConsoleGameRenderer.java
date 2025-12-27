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
            sb.append(String.format(" %d  ", x));
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
            String unitId = unit.getId();
            sb.append(String.format("%3s |", unitId));
        } else {
            sb.append(" ").append(game.getBattlefield().getTerrainAt(pos).getIcon()).append(" |");
        }
    }

    private void renderLegend(StringBuilder sb) {
        sb.append(System.lineSeparator()).append("Legende Terrain:");
        for (Terrain terrain : Terrain.values()) {
            sb.append("  ").append(terrain.getIcon()).append(" ")
                    .append(terrain.getGermanName()).append(" | ");
        }
        sb.append(System.lineSeparator());
        sb.append("Einheiten: F1-F3 (Feuer), W1-W3 (Wasser), E1-E3 (Erde), A1-A3 (Luft)");
        sb.append(System.lineSeparator()).append(System.lineSeparator());
    }

    private void renderUnitSummary(StringBuilder sb, Game game) {
        long livingUnits = game.getUnits().stream().filter(Unit::isAlive).count();
        sb.append("Lebende Einheiten: ").append(livingUnits)
                .append("/").append(game.getUnits().size()).append(System.lineSeparator());
    }
}
