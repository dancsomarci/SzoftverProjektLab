package model.strategy;

import model.Virologist;
import model.agents.Bear;
import model.map.Field;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Medve vírus hatását reprezentálja ez az IMoveStr: Csak random tud a fertőzött virológus lépni
 * valamint mindenkit megfertőz a Bear a vírussal, aki azon a mezőn tartózkodik, ahova lép.
 */
public class BearMove implements IMoveStr{
    /**
     * Elvégzi a léptetést, de nem a megadott Field-re
     * hanem egy random szomszédosra, és az ott lévő virológusokat megpróbálja megkenni
     * Bear ágenssel.
     * @param v Mozgó virológus
     * @param from Virológus aktuális mezője
     * @param to Új mező, amelyre lépni szeretne
     */
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
