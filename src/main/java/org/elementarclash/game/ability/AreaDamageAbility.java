package org.elementarclash.game.ability;

import org.elementarclash.game.Game;
import org.elementarclash.game.combat.DamageCalculator;
import org.elementarclash.game.combat.DamageResult;
import org.elementarclash.game.command.ValidationResult;
import org.elementarclash.units.Unit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Ability that damages all adjacent enemy units.
 * Used by Earthquake Titan (Earth faction).
 * <p>
 * Design Pattern: Command Pattern (#8) + Composite Pattern (#3)
 * Why: Active abilities follow validate → execute → undo lifecycle.
 * Uses Composite's area-based unit queries to find adjacent enemies.
 * <p>
 * Effect: Deals damage to all adjacent enemies (uses normal damage calculation).
 * Damage is calculated per target using DamageCalculator (terrain effects apply).
 * <p>
 * Tactical Use: Punishes clustering enemies, rewards careful positioning.
 * Cooldown prevents spam and maintains balance.
 */
public class AreaDamageAbility implements Ability<AreaDamageAbility.UndoState> {

    private static final int COOLDOWN = 2;
    private static final double DAMAGE_MULTIPLIER = 0.75;

    private final DamageCalculator damageCalculator;

    public AreaDamageAbility() {
        this.damageCalculator = new DamageCalculator();
    }

    @Override
    public String getName() {
        return "Erderschütterung";
    }

    @Override
    public String getDescription() {
        return "Greift alle angrenzenden Feinde an (75% Schaden, Abklingzeit: " + COOLDOWN + " Runden)";
    }

    @Override
    public ValidationResult validate(Game game, Unit actor, Object[] targets) {
        List<Unit> adjacentEnemies = getAdjacentEnemies(game, actor);

        if (adjacentEnemies.isEmpty()) {
            return ValidationResult.failure("Keine angrenzenden Feinde vorhanden");
        }

        return ValidationResult.success();
    }

    @Override
    public UndoState execute(Game game, Unit actor, Object[] targets) {
        List<Unit> adjacentEnemies = getAdjacentEnemies(game, actor);
        Map<Unit, Integer> damageDealt = new HashMap<>();

        for (Unit enemy : adjacentEnemies) {
            DamageResult result = damageCalculator.calculateDamage(actor, enemy, game);
            int reducedDamage = (int) (result.totalDamage() * DAMAGE_MULTIPLIER);

            int previousHealth = enemy.getCurrentHealth();
            enemy.takeDamage(reducedDamage);
            int actualDamage = previousHealth - enemy.getCurrentHealth();

            damageDealt.put(enemy, actualDamage);
        }

        return new UndoState(damageDealt);
    }

    @Override
    public void undo(Game game, Unit actor, UndoState undoState) {
        for (Map.Entry<Unit, Integer> entry : undoState.damageDealt.entrySet()) {
            Unit target = entry.getKey();
            int damageToReverse = entry.getValue();
            target.heal(damageToReverse);
        }
    }

    @Override
    public int getCooldown() {
        return COOLDOWN;
    }

    private List<Unit> getAdjacentEnemies(Game game, Unit actor) {
        List<Unit> adjacentUnits = game.getUnitsAdjacentTo(actor.getPosition());
        List<Unit> enemies = new ArrayList<>();

        for (Unit unit : adjacentUnits) {
            if (unit.getFaction() != actor.getFaction() && unit.isAlive()) {
                enemies.add(unit);
            }
        }

        return enemies;
    }

    public record UndoState(Map<Unit, Integer> damageDealt) {
    }
}
