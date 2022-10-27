package view;

/**
 * Megfigyelő osztály, lehet értesíteni a feliratkozott objektumok felől
 * ha változás történt
 */
public abstract class Observer{
    /**
     * A megfigyelőt értesítő függvény
     */
    public abstract void update();
}
