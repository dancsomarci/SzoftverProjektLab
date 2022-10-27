package view;

import model.map.Warehouse;

//TODO comment

public class DrawableWarehouse extends Warehouse  implements Drawable {
    @Override
    public String getTexture() {

        return "/textures/Warehouse.png";
    }
}
