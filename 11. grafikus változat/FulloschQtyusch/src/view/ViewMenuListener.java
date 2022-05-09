package view;

import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
//TODO comment
public class ViewMenuListener implements MenuListener {

    Runnable action;

    public ViewMenuListener(Runnable action){
        this.action = action;
    }

    @Override
    public void menuSelected(MenuEvent e) {
        action.run();
    }

    @Override
    public void menuDeselected(MenuEvent e) {
    }

    @Override
    public void menuCanceled(MenuEvent e) {
    }
}
