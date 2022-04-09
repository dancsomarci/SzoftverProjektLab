package model.map;

import model.Virologist;
import model.agents.Bear;
import model.codes.GeneticCode;

public class InfectedLaboratory extends Laboratory{
    /**
     * Genetikai kód hozzáadása a mezőhöz
     *
     * @param c hozzáadandó genetikai kód
     */
    public InfectedLaboratory(GeneticCode c) {
        super(c);
    }

    @Override
    public void AddVirologist(Virologist v){
        v.TargetedWith(new Bear());
        super.AddVirologist(v);
    }
}
