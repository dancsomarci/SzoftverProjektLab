package model.strategy;

import model.Virologist;

/**
 * Virológus támadását szimbolizáló interface. (Strategy pattern)
 */
public interface IAttackStr {

    /**
     * A támadást szimbolizáló metódus.
     * @param attacker A támadó virológus.
     * @param target A megtámadott virológus.
     */
    void Attack(Virologist attacker, Virologist target);
}
