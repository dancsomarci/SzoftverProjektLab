package view;

import model.map.Field;

/**
 * Az általános mező képernyőre kirajzolható változata.
 */
public class DrawableField extends Field  implements Drawable {
    /**
     * Visszatér a mező textúrájának az elérési útjával.
     *
     * @return Az elérési út
     */
    @Override
    public String getTexture() {
        return "/textures/Field.png";
    }
}
