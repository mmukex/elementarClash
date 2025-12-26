package org.elementarclash.battlefield;

import java.util.ArrayList;
import java.util.List;

/**
 * Composite representing a collection of battlefield cells.
 * Can represent both fixed rows (10 cells per row in grid structure)
 * and arbitrary dynamic regions (for area effects like forest fires, earthquakes, or geysirs).
 *
 * Design Pattern: Composite (GoF #3) - Composite
 * Why: Unified representation for both grid structure and dynamic areas.
 *      Allows applying operations to cell collections without duplicating logic.
 *      Examples:
 *      - Grid structure: Battlefield organizes 10 regions (rows) of 10 cells each
 *      - Dynamic effects: Apply ForestFireEffect to 3x3 region around fire source
 */
public record Region(List<Cell> cells) implements BattlefieldComponent {

    public Region(List<Cell> cells) {
        this.cells = new ArrayList<>(cells);
    }

    @Override
    public List<Cell> cells() {
        return new ArrayList<>(cells);
    }
}
