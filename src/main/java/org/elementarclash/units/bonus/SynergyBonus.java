package org.elementarclash.units.bonus;

import org.elementarclash.game.Game;
import org.elementarclash.units.Faction;
import org.elementarclash.units.Unit;

/**
 * Synergy bonus from adjacent allied units.
 * <p>
 * Example (README): "Benachbarte Feuer-Einheiten gewähren einander +1 Angriff"
 */
public class SynergyBonus extends UnitDecorator {

    private final Game game;
    private final Faction faction;

    public SynergyBonus(Game game, Faction faction) {
        this.game = game;
        this.faction = faction;
    }

    @Override
    public int getAttackBonus(Unit unit) {
        // Fire faction: +1 attack per adjacent ally
        if (faction == Faction.FIRE) {
            long adjacentAllies = game.getUnitsAdjacentTo(unit.getPosition())
                    .stream()
                    .filter(u -> u.getFaction() == faction)
                    .filter(Unit::isAlive)
                    .count();

            return (int) adjacentAllies;
        }

        // Other factions: no synergy bonus (for now)
        return 0;
    }

    @Override
    public int getDefenseBonus(Unit unit) {
        // Future: Earth faction could have defensive synergy
        return 0;
    }

    @Override
    public int getMovementBonus(Unit unit) {
        return 0;
    }

    @Override
    public boolean isExpired() {
        return false; // Recalculated dynamically
    }

    @Override
    public void tick() {
        // No tick logic (dynamic calculation)
    }

    @Override
    public String getDecoratorName() {
        return "SynergyBonus";
    }

    @Override
    public String getDescription() {
        return "Synergy bonus from adjacent allies";
    }
}