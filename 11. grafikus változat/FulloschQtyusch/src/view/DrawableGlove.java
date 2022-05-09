package view;

import model.equipments.Glove;

/**
 * A kesztyűnek a képernyőre kirajzolható változata.
 */
public class DrawableGlove extends Glove implements Drawable {

    /**
     * Visszatér a kesztyű textúrájának az elérési útjával.
     *
     * @return Az elérési út
     */
    @Override
    public String getTexture(){
        return "textures/kesztyu.png";
    }

}
