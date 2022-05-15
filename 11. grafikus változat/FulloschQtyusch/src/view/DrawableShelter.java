package view;

import model.equipments.Equipment;
import model.map.Shelter;

/**
 * A óvóhelynek a képernyőre kirajzolható változata.
 */
public class DrawableShelter extends Shelter  implements Drawable {

    /**
     * Védőfelszerelés hozzáadása
     *
     * @param e hozzáadandó felszerelés
     */
    public DrawableShelter(Equipment e) {
        super(e);
    }

    /**
     * Visszatér a óvóhely textúrájának az elérési útjával.
     *
     * @return Az elérési út
     */
    @Override
    public String getTexture() {
        return "/textures/Shelter.png";
    }

}
