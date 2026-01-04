package org.elementarclash.game.combat.handler;

import org.elementarclash.game.combat.DamageContext;

/**
 * Chain of Responsibility Pattern (GoF #9)
 *
 * Abstract handler for damage calculation pipeline.
 * Each handler adds its modifiers and passes to next handler.
 *
 * Why Chain of Responsibility?
 * - Damage calculation involves multiple independent steps:
 *   Base Damage → Faction Advantage → Terrain → Synergies → Defense
 * - Each step is isolated and testable
 * - Easy to add new modifiers (e.g., weather effects, artifacts)
 * - Clear separation of concerns
 *
 * @author @crstmkt
 */
public abstract class DamageHandler {

    private DamageHandler next;

    /**
     * Set next handler in chain.
     * @return next handler (for fluent chaining)
     */
    public DamageHandler setNext(DamageHandler next) {
        this.next = next;
        return next;
    }

    /**
     * Handle damage calculation step.
     * Subclasses implement their logic, then call super.handle(context).
     *
     * @param context shared damage calculation context
     */
    public void handle(DamageContext context) {
        if (next != null) {
            next.handle(context);
        }
    }

    /**
     * Get handler name for logging/debugging.
     */
    public abstract String getHandlerName();
}