package control;

import model.Game;
import view.Window;

public class Main {

    /**
     * Alkalmazás belépési pontja.
     * @param args parancsori argumentumok.
     */
    public static void main(String[] args){
        try {
            new Window();
            //Controller controller = new Controller(Game.Create());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
