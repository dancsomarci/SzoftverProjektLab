package model.equipments;

import model.Virologist;
import model.strategy.IAttackStr;

public class Axe extends Equipment implements IAttackStr {
    private boolean used = false;

    @Override
    public void ApplyStrategy(Virologist v) {
        if (!used){
            v.SetAttackStr(this);
        }
    }

    @Override
    public void Attack(Virologist attacker, Virologist target) {
        attacker.DecreaseActions();
        target.Kill();
        used = false;
        attacker.Reset();
    }
}
