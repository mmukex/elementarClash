package org.elementarclash.battlefield;

import java.util.ArrayList;
import java.util.List;

/**
 * Composite representing an arbitrary subset of battlefield cells.
 * Used for area effects like forest fires, earthquakes, or geysirs.
 *
 * Design Pattern: Composite (GoF #3) - Composite
 * Why: Allows applying effects to dynamically defined areas without affecting entire battlefield.
 *      Example: Apply ForestFireEffect only to 3x3 region around fire source.
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
