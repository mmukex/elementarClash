package org.elementarclash.battlefield;

import java.util.List;
import java.util.function.Consumer;

/**
 * Component interface for Composite Pattern in battlefield hierarchy.
 * Allows treating individual cells and groups of cells uniformly.
 * <p>
 * Design Pattern: Composite (GoF #3)
 * Why: Enables operations on both single cells (Leaf) and regions/entire battlefield (Composite).
 * Example: Apply forest fire effect to single cell, 3x3 region, or entire battlefield
 * with the same interface.
 */
public interface BattlefieldComponent {
    /**
     * Returns all cells in this component.
     *
     * @return list of cells
     */
    List<Cell> cells();

    /**
     * Retrieves cell at specified index.
     *
     * @param index cell index
     * @return cell at index
     */
    default Cell getCell(int index) {
        return cells().get(index);
    }

    /**
     * Applies an effect to all cells in this component.
     * Used for area-of-effect operations like forest fires, earthquakes, geysers.
     * <p>
     * Integration: Enables dynamic events (Observer Pattern #7 @crstmkt).
     * Events can apply effects to arbitrary regions using this method.
     * <p>
     * Example:
     * <pre>
     * region.applyEffect(cell -> {
     *     if (cell.getTerrain() == Terrain.FOREST) {
     *         cell.setTerrain(Terrain.LAVA);
     *     }
     * });
     * </pre>
     *
     * @param effect consumer that modifies each cell
     */
    default void applyEffect(Consumer<Cell> effect) {
        cells().forEach(effect);
    }
}
