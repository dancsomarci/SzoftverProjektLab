package view;

import model.equipments.Cloak;

/**
 * A köpenynek a képernyőre kirajzolható változata.
 */
public class DrawableCloak extends Cloak implements Drawable {

    /**
     * Visszatér a köpeny textúrájának az elérési útjával.
     *
     * @return Az elérési út
     */
    @Override
    public String getTexture(){
        return "/textures/cloakSlot.png";
    }

}
