package model.strategy;

import model.Virologist;
import model.agents.Bear;
import model.map.Field;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class BearMove implements IMoveStr{
    @Override
    public void Move(Virologist v, Field from, Field to) {

        ArrayList<Field> neighbours = from.GetNeighbours();
        from.RemoveVirologist(v);
        Field randomNeighbour;
        if (v.GetContext().randOn) {
            randomNeighbour = neighbours.get(ThreadLocalRandom.current().nextInt(0, neighbours.size()));
        } else{
            randomNeighbour = neighbours.get(0);
        }
        randomNeighbour.AddVirologist(v);
        randomNeighbour.DestroyMaterial();
        for (Virologist vir : randomNeighbour.GetVirologists()) {
            vir.TargetedWith(new Bear());
        }
        v.DecreaseActions();
    }
}
