package view;

import model.equipments.Equipment;
import model.map.Shelter;

public class DrawableShelter extends Shelter  implements Drawable {
    /**
     * Védőfelszerelés hozzáadása
     *
     * @param e hozzáadandó felszerelés
     */
    public DrawableShelter(Equipment e) {
        super(e);
    }

    @Override
    public String getTexture() {
        // TODO
        return null;
    }
}
