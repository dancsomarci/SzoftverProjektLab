package model.equipments;

import model.Virologist;
import model.strategy.IAttackStr;

/**
 * Egy baltát szimbolizáló Felszerelés, amivel lehetséges megtámadni egy másik virológust.
 */
public class Axe extends Equipment implements IAttackStr {
    /**
     * A baltát csak egyszer lehet hasznáni, utána kicsorbul, ekkor igaz lesz ennek a tagváltozónak az értéke.
     */
    private boolean used = false;

    /**
     * Saját maga által implementált stratégiát helyez el a virológuson, de csak akkor ha nem használták még a fegyvert.
     * @param v viselő virológus
     */
    @Override
    public void ApplyStrategy(Virologist v) {
        if (!used){
            v.SetAttackStr(this);
        }
    }

    /**
     * A stratégiáját megvalósító függvény, amely kicsorbítj a baltát, és megtámadja a másik virológust.
     * @param attacker A támadó
     * @param target A célpont
     */
    @Override
    public void Attack(Virologist attacker, Virologist target) {
        attacker.DecreaseActions();
        target.Kill();
        used = true;
        attacker.Reset();
    }
}
