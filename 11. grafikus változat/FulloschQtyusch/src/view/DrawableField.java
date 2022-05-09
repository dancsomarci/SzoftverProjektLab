package view;

import model.map.Field;

//TODO comment

public class DrawableField extends Field  implements Drawable {
    @Override
    public String getTexture() {
        return "textures/Field.png";
    }
}
