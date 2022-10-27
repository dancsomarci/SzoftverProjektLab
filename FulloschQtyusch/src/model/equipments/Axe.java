package model.equipments;

import model.Virologist;
import model.strategy.IAttackStr;

/**
 * Olyan védőfelszerelés, amely egyben támadási stratégia is
 * Egyszeri támadást biztosít, ezzel megölhető vele egy virológus
 */
public class Axe extends Equipment implements IAttackStr {
    /**
     * Használtság, default nem használt
     */
    private boolean used = false;

    /**
     * Ha nem használt, alkalmazza támadási stratégiaként önmagát
     * a paraméterül kapott virológuson
     * @param v viselő virológus
     */
    @Override
    public void ApplyStrategy(Virologist v) {
        if (!used){
            v.SetAttackStr(this);
        }
    }

    /**
     * Megtámadja a paraméterül kapott virológust
     * @param attacker támadó virológus
     * @param target megtámadott virológus
     */
    @Override
    public void Attack(Virologist attacker, Virologist target) {
        attacker.DecreaseActions();
        target.Kill();
        used = true;
        attacker.Reset();
    }
}
