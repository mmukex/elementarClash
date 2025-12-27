package org.elementarclash.units.fire;

import org.elementarclash.faction.Faction;
import org.elementarclash.units.Unit;
import org.elementarclash.units.UnitStats;
import org.elementarclash.units.UnitType;
import org.elementarclash.units.strategy.movement.GroundMovementStrategy;
import org.elementarclash.units.strategy.attack.MeleeAttackStrategy;

/**
 * Fire faction's front-line melee warrior with lava terrain bonus.
 * <p>
 * Faction: Fire
 * <p>
 * Movement: Ground (Fire faction terrain modifiers: Lava cost 1.0, Ice cost 2.0)
 * Attack: Melee (range: 1)
 * <p>
 * Special Ability: +2 attack on Lava terrain.
 * Inferno Warriors gain combat advantage when fighting on volcanic ground.
 * <p>
 * Tactical Use: Tanky frontline fighter.
 * High health and defense. Strongest on lava terrain. Weak against ranged units and air.
 */
class InfernoWarrior extends Unit {
    private static final int LAVA_ATTACK_BONUS = 2;

    public InfernoWarrior(String id, UnitStats stats) {
        super(id, "Inferno-Krieger", Faction.FIRE, UnitType.INFERNO_WARRIOR, stats);
        setMovementStrategy(new GroundMovementStrategy(Faction.FIRE));
        setAttackStrategy(new MeleeAttackStrategy());
    }

    @Override
    public String getSpecialAbility() {
        return "+2 Angriff auf Lava-Gelände";
    }
}