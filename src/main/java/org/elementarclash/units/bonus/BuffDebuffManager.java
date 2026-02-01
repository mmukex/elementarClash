package org.elementarclash.units.bonus;

import org.elementarclash.game.Game;
import org.elementarclash.units.Unit;
import org.elementarclash.units.bonus.temporary.*;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class BuffDebuffManager {
    private static final Random RANDOM = new Random();

    // Wahrscheinlichkeits-Parameter (konfigurierbar)
    private static final double BASE_CHANCE = 0.03;      // 3%
    private static final double INCREASE_PER_ROUND = 0.06; // 6% pro Runde
    private static final double MAX_CHANCE = 0.60;       // 60% cap

    // Buff-Pool (positive Effekte)
    private static final List<Supplier<UnitDecorator>> BUFF_POOL = List.of(
            AttackBuffDecorator::new,
            DefenseBuffDecorator::new,
            HastenedDecorator::new
    );

    // Debuff-Pool (negative Effekte)
    private static final List<Supplier<UnitDecorator>> DEBUFF_POOL = List.of(
            AttackDebuffDecorator::new,
            DefenseDebuffDecorator::new,
            SlowedDecorator::new

    );

    /**
     * Versucht, einen Buff/Debuff zu vergeben.
     *
     * @param game         Spielinstanz
     * @param currentRound aktuelle Rundennummer
     * @return true wenn Effekt vergeben wurde
     */
    public void tryApplyRandomEffect(Game game, int currentRound) {
        // 1. Wahrscheinlichkeit berechnen
        double chance = calculateChance(currentRound);

        // 2. Würfeln
        if (RANDOM.nextDouble() > chance) {
            return; // Kein Effekt dieses Mal
        }

        // 3. Zufällige lebende Unit der aktiven Fraktion wählen
        List<Unit> eligibleUnits = game.getUnits().stream()
                .filter(Unit::isAlive)
                .filter(u -> u.getFaction() == game.getActiveFaction())
                .toList();

        if (eligibleUnits.isEmpty()) {
            return; // Keine gültigen Targets
        }

        Unit target = eligibleUnits.get(RANDOM.nextInt(eligibleUnits.size()));

        // 4. Buff oder Debuff? (50/50)
        boolean isBuff = RANDOM.nextBoolean();
        // 5. Zufälligen Effekt aus Pool wählen
        List<Supplier<UnitDecorator>> pool = isBuff ? BUFF_POOL : DEBUFF_POOL;
        UnitDecorator decorator = pool.get(RANDOM.nextInt(pool.size())).get();

        // 6. Decorator anwenden
        target.addDecorator(decorator);

        // 8. Log-Ausgabe
        String effectType = isBuff ? "BUFF" : "DEBUFF";
        System.out.println("[Event] " + effectType + " applied: " +
                target.getName() + " received " + decorator.getDescription());
    }

    /**
     * Berechnet Wahrscheinlichkeit basierend auf Rundennummer.
     * Formel: BASE_CHANCE + ((currentRound-1) * INCREASE_PER_ROUND), capped bei MAX_CHANCE
     */
    private double calculateChance(int currentRound) {
        double chance = BASE_CHANCE + ((currentRound - 1) * INCREASE_PER_ROUND);
        return Math.min(chance, MAX_CHANCE);
    }
}