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
    List<Cell> cells();

    default void applyEffect(TerrainEffect effect) {
        cells().forEach(cell -> cell.applyEffect(effect));
    }

    default Cell getCell(int index) {
        return cells().get(index);
    }
}
