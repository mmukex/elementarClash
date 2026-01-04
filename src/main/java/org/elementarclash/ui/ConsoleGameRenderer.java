package org.elementarclash.ui;

import org.elementarclash.battlefield.Battlefield;
import org.elementarclash.battlefield.Terrain;
import org.elementarclash.game.Game;
import org.elementarclash.game.combat.DamageResult;
import org.elementarclash.units.Unit;
import org.elementarclash.util.Position;
import org.elementarclash.game.event.*;

/**
 * Renders game state as ASCII text for console output.
 */
public class ConsoleGameRenderer implements GameRenderer, GameObserver {

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

    // ===== OBSERVER PATTERN =====
    @Override
    public void onEvent(GameEvent event) {
        switch (event.getEventType()) {
            case UNIT_MOVED -> handleUnitMoved((UnitMovedEvent) event);
            case UNIT_ATTACKED -> handleUnitAttacked((UnitAttackedEvent) event);
            case UNIT_DIED -> handleUnitDeath((UnitDeathEvent) event);
            case TERRAIN_CHANGED -> handleTerrainChanged((TerrainChangedEvent) event);
            case TURN_ENDED -> handleTurnEnded((TurnEndedEvent) event);
            case GAME_OVER -> handleGameOver((GameOverEvent) event);
            default -> {}  // Ignore other events
        }
    }

    private void handleUnitMoved(UnitMovedEvent event) {
        System.out.println("[Move] " + event.getDescription());
        // Optional: partial re-render (only affected cells)
    }

    private void handleUnitAttacked(UnitAttackedEvent event) {
        System.out.println("[Attack] " + event.getDescription());

        // Show damage breakdown (from Chain of Responsibility)
        DamageResult result = event.getDamageResult();
        System.out.println("  Base: " + result.baseDamage() +
                ", Faction: ×" + result.factionMultiplier() +
                ", Terrain: +" + result.terrainAttackBonus() +
                ", Synergy: +" + result.synergyBonus() +
                ", Defense: -" + result.totalDefense() +
                " = " + result.totalDamage() + " total");
    }

    private void handleUnitDeath(UnitDeathEvent event) {
        System.out.println("[Death] " + event.getDescription());
        System.out.println("  💀 " + event.getUnit().getName() + " has fallen!");
    }

    private void handleTerrainChanged(TerrainChangedEvent event) {
        System.out.println("[Terrain] " + event.getDescription());
    }

    private void handleTurnEnded(TurnEndedEvent event) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("  " + event.getFaction().name() + "'s turn ended");
        System.out.println("=".repeat(50) + "\n");
    }

    private void handleGameOver(GameOverEvent event) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("       🏆 GAME OVER - " + event.getWinner().name() + " WINS! 🏆");
        System.out.println("=".repeat(60) + "\n");
    }

    @Override
    public String getObserverName() {
        return "ConsoleUIObserver";
    }
}
