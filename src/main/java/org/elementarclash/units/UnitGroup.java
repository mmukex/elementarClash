package org.elementarclash.units;

import java.util.ArrayList;
import java.util.List;

/**
 * Composite representing a group of units or nested unit groups.
 * Used for faction armies, synergy calculations, and group operations.
 * <p>
 * Design Pattern: Composite (GoF #3) - Composite
 * Why: Allows treating collections of units as single entity.
 * Supports nesting for hierarchical army structures.
 * Example: Fire faction army with all fire units for adjacency bonus calculation.
 */
public class UnitGroup implements UnitComponent {

    private final List<UnitComponent> units;

    public UnitGroup(List<? extends UnitComponent> units) {
        this.units = new ArrayList<>(units);
    }

    public void add(UnitComponent unit) {
        units.add(unit);
    }

    public void remove(UnitComponent unit) {
        units.remove(unit);
    }

    @Override
    public List<Unit> getAllUnits() {
        return units.stream()
                .flatMap(unit -> unit.getAllUnits().stream())
                .toList();
    }

    @Override
    public int getTotalHealth() {
        return units.stream()
                .mapToInt(UnitComponent::getTotalHealth)
                .sum();
    }

    @Override
    public boolean isAlive() {
        return units.stream().anyMatch(UnitComponent::isAlive);
    }
}
