package view;

import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

/**
 * Az ablak tetején elhelyezkedő menüsáv elemeire való kattintás eseményeket figyelő ösztály.
 */
public class ViewMenuListener implements MenuListener {

    /**
     * A művelet, ami kattintáskor lefut.
     */
    Runnable action;

    /**
     * Létrehoz egy eseményfigyelőt, ami kattintáskor a megadott műveletet futtatja.
     *
     * @param action A művelet
     */
    public ViewMenuListener(Runnable action){
        this.action = action;
    }

    /**
     * A menü egy elemének kiválasztásakor meghívott függvény.
     *
     * @param e Az esemény
     */
    @Override
    public void menuSelected(MenuEvent e) {
        action.run();
    }

    /**
     * A menü egy kiválasztott elemének leválasztásakor hívott függvény.
     * Üres, nem végez műveletet.
     *
     * @param e Az esemény
     */
    @Override
    public void menuDeselected(MenuEvent e) {
    }

    /**
     * A menü egy elemének törlésekor hívott függvény.
     * Üres, nem végez műveletet.
     *
     * @param e Az esemény
     */
    @Override
    public void menuCanceled(MenuEvent e) {
    }
}
