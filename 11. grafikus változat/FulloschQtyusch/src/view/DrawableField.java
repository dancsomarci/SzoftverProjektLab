package view;

import model.map.Field;

public class DrawableField extends Field  implements Drawable {
    @Override
    public String getTexture() {
        String picturePath = "textures/Field.png";
        return picturePath;
    }
}
