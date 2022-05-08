package view;

/**
 * Megfigyelõ osztály, lehet értesíteni a feliratkozott objektumok felõl
 * ha változás történt
 */
public abstract class Observer{
    /**
     * A megfigyelõt értesítõ függvény
     */
    public abstract void update();
}
