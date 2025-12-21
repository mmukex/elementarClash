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
    List<Unit> getAllUnits();

    int getTotalHealth();

    boolean isAlive();
}
