package org.elementarclash.units;

import lombok.Getter;
import org.elementarclash.faction.Faction;

/**
 * Abstract Factory for creating faction-specific units.
 * <p>
 * Design Pattern: Factory Method
 * - Each faction has its own concrete factory (FireUnitFactory, etc.)
 * - Encapsulates unit creation logic
 * - Makes adding new factions easy without modifying existing code
 *
 * @author @crstmkt
 */
public abstract class UnitFactory {
    @Getter
    private final Faction faction;
    private int unitCounter = 0;

    protected UnitFactory(Faction faction) {
        this.faction = faction;
    }

    /**
     * Factory Method - creates a unit of the specified type.
     * Delegates to concrete factories for faction-specific implementation.
     *
     * @param type The type of unit to create
     * @return A new unit instance
     * @throws IllegalArgumentException if type doesn't belong to this faction
     */
    public final Unit createUnit(UnitType type) {
        if (!isValidTypeForFaction(type)) {
            throw new IllegalArgumentException(
                    "Unit type " + type + " is not valid for faction " + faction
            );
        }

        String id = generateUnitId(type);
        return createUnitInternal(id, type);
    }

    /**
     * Template Method - subclasses implement faction-specific creation.
     */
    protected abstract Unit createUnitInternal(String id, UnitType type);

    /**
     * Validates that a unit type belongs to this faction.
     */
    protected abstract boolean isValidTypeForFaction(UnitType type);

    /**
     * Generates a unique ID for units (e.g., "F1", "W2", "E3").
     */
    private String generateUnitId(UnitType type) {
        unitCounter++;
        return faction.name().substring(0, 1) + unitCounter;
    }
}