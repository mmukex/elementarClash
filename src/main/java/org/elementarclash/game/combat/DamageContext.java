package org.elementarclash.game.combat;

import lombok.Getter;
import org.elementarclash.game.Game;
import org.elementarclash.units.Unit;

import java.util.ArrayList;
import java.util.List;

/**
 * Shared context for Chain of Responsibility damage calculation.
 * Handlers read from and write to this context.
 *
 * @author @crstmkt
 */
@Getter
public class DamageContext {

    private final Unit attacker;
    private final Unit target;
    private final Game game;

    // Damage calculation stages
    private int baseDamage = 0;
    private double factionMultiplier = 1.0;
    private int terrainAttackBonus = 0;
    private int terrainDefenseBonus = 0;
    private int synergyBonus = 0;
    private int totalDefense = 0;
    private int finalDamage = 0;

    // Logging/debugging
    private final List<String> calculationSteps = new ArrayList<>();

    public DamageContext(Unit attacker, Unit target, Game game) {
        this.attacker = attacker;
        this.target = target;
        this.game = game;
    }

    // ===== SETTERS (für Handlers) =====

    public void setBaseDamage(int baseDamage) {
        this.baseDamage = baseDamage;
        logStep("Base Damage: " + baseDamage);
    }

    public void applyFactionMultiplier(double multiplier) {
        this.factionMultiplier = multiplier;
        int oldDamage = baseDamage;
        this.baseDamage = (int) Math.round(baseDamage * multiplier);
        logStep("Faction Advantage: ×" + multiplier + " (" + oldDamage + " → " + baseDamage + ")");
    }

    public void addTerrainAttackBonus(int bonus) {
        this.terrainAttackBonus += bonus;
        logStep("Terrain Attack Bonus: +" + bonus);
    }

    public void addTerrainDefenseBonus(int bonus) {
        this.terrainDefenseBonus += bonus;
        logStep("Terrain Defense Bonus: +" + bonus);
    }

    public void addSynergyBonus(int bonus) {
        this.synergyBonus += bonus;
        logStep("Synergy Bonus: +" + bonus);
    }

    public void setTotalDefense(int defense) {
        this.totalDefense = defense;
        logStep("Total Defense: " + defense);
    }

    public void setFinalDamage(int damage) {
        this.finalDamage = damage;
        logStep("Final Damage: " + damage);
    }

    // ===== CALCULATION HELPERS =====

    /**
     * Get total attack before defense.
     */
    public int getTotalAttack() {
        return baseDamage + terrainAttackBonus + synergyBonus;
    }

    /**
     * Convert to DamageResult for consumption.
     */
    public DamageResult toResult() {
        return new DamageResult(
                finalDamage,
                baseDamage,
                factionMultiplier,
                terrainAttackBonus,
                terrainDefenseBonus,
                synergyBonus,
                totalDefense,
                new ArrayList<>(calculationSteps)
        );
    }

    // ===== LOGGING =====

    private void logStep(String step) {
        calculationSteps.add(step);
    }
}