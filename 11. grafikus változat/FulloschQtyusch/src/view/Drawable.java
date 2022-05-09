package view;

/**
 * Egy a képernyőre kirajzolható objektumot reprezentáló interfész.
 */
public interface Drawable {

    /**
     * Visszatér a textúra elérési útjával, ami a képernyőn meg fog jelenni.
     *
     * @return Az elérési út
     */
    String getTexture();

}
