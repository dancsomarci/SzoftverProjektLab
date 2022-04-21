package model.strategy;

import model.Virologist;

/**
 * Default támadási viselkedést megvalósító osztály.
 * Nem végez támadást, mert a játékmechanikában ez az alapértelmezett viselkedés. (támadáshoz kell egy fegyver)
 * Részletesebb leírásért lásd: dokumentáció)
 */
public class DefAttack implements IAttackStr{
    /**
     * Csak akciópontot von le a támadótól, de nem végez tényleges támadást.
     * @param attacker A támadó virológus.
     * @param target A megtámadott virológus.
     */
    @Override
    public void Attack(Virologist attacker, Virologist target) {
        attacker.DecreaseActions();
    }
}
