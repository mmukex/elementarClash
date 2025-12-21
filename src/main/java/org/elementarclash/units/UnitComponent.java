package org.elementarclash.units;

import java.util.List;

/**
 * Component interface for Composite Pattern in unit hierarchy.
 * Allows treating individual units and groups of units uniformly.
 * <p>
 * Design Pattern: Composite (GoF #3)
 * Why: Enables operations on both single units (Leaf) and armies/groups (Composite).
 * Example: Calculate total health of single unit or entire faction army
 * with the same interface.
 * <p>
 * Use cases:
 * - Apply faction synergies to all units in group
 * - Calculate combined army statistics
 * - Nested grouping (UnitGroup containing other UnitGroups)
 */
public interface UnitComponent {
    /**
     * Returns all individual units in this component (flattened list).
     * For single units: returns list containing itself.
     * For groups: returns all nested units recursively.
     *
     * @return list of all units
     */
    List<Unit> getAllUnits();

    /**
     * Returns combined health of all units in this component.
     *
     * @return total health points
     */
    int getTotalHealth();

    /**
     * Returns whether any unit in this component is alive.
     *
     * @return true if at least one unit is alive
     */
    boolean isAlive();
}
