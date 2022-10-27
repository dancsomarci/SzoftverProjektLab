package model.strategy;

import model.Virologist;
import model.agents.Bear;
import model.map.Field;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * TODO comment
 */
public class BearMove implements IMoveStr{
    @Override
    public void Move(Virologist v, Field from, Field to) {

        ArrayList<Field> neighbours = from.GetNeighbours();
        from.RemoveVirologist(v);
        Field randomNeighbour;

        randomNeighbour = neighbours.get(ThreadLocalRandom.current().nextInt(0, neighbours.size()));

        randomNeighbour.AddVirologist(v);
        randomNeighbour.DestroyMaterial();
        for (Virologist vir : randomNeighbour.GetVirologists()) {
            vir.TargetedWith(new Bear());
        }
        v.DecreaseActions();
    }
}
