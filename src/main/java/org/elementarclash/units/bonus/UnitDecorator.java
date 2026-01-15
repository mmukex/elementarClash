package org.elementarclash.units.bonus;

import org.elementarclash.units.Unit;

/**
 * Decorator Pattern (GoF #4) - Unit Buffs/Debuffs
 *
 * Wraps Unit with additional stat bonuses/penalties.
 * Multiple decorators can be stacked (terrain + synergy + abilities).
 *
 * Why Decorator Pattern?
 * - Units receive dynamic bonuses from multiple sources:
 *   * Terrain (via @mmukex Visitor Pattern)
 *   * Adjacent allies (synergy)
 *   * Abilities (temporary buffs)
 * - Decorator allows stacking without modifying Unit class
 * - Clean separation: Unit = base stats, Decorators = modifiers
 *
 * @author @crstmkt
 *
 * ToDo: Buff wie Tod auf einem Feld oder Fee für tempörere Buffs oder Debuffs in Kombi mit Observer kann das gut klappen
 */
public abstract class UnitDecorator {

    /**
     * Attack bonus from this decorator.
     */
    public abstract int getAttackBonus(Unit unit);

    /**
     * Defense bonus from this decorator.
     */
    public abstract int getDefenseBonus(Unit unit);

    /**
     * Is this decorator expired? (for temporary buffs)
     */
    public abstract boolean isExpired();

    /**
     * Tick at end of turn (for cooldowns/durations).
     */
    public abstract void tick();

    /**
     * Get decorator name for debugging/UI.
     */
    public abstract String getDecoratorName();

    /**
     * Get description for UI (e.g., "+2 Attack on Lava").
     */
    public abstract String getDescription();
}