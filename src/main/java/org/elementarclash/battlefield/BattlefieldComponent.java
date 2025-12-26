package org.elementarclash.battlefield;

import java.util.List;

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
     * Applies a terrain effect to all cells in this component.
     *
     * @param effect effect to apply
     */
    default void applyEffect(TerrainEffect effect) {
        cells().forEach(cell -> cell.applyEffect(effect));
    }

    /**
     * Retrieves cell at specified index.
     *
     * @param index cell index
     * @return cell at index
     */
    default Cell getCell(int index) {
        return cells().get(index);
    }
}
