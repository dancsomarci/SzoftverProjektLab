package view;

import model.map.Warehouse;

/**
 * A raktárnak a képernyőre kirajzolható változata.
 */
public class DrawableWarehouse extends Warehouse  implements Drawable {

    /**
     * Visszatér a raktár textúrájának az elérési útjával.
     *
     * @return Az elérési út
     */
    @Override
    public String getTexture() {
        return "/textures/Warehouse.png";
    }

}
