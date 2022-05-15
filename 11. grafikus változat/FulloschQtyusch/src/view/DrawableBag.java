package view;

import model.equipments.Bag;

/**
 * A zsáknak a képernyőre kirajzolható változata.
 */
public class DrawableBag extends Bag implements Drawable {

    /**
     * Visszatér a zsák textúrájának az elérési útjával.
     *
     * @return Az elérési út
     */
    @Override
    public String getTexture(){
        return "/textures/BagSlot.png";
    }

}
