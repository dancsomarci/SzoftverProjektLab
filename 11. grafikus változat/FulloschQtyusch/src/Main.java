import control.Controller;
import control.Loader;

/**
 * Az alkalmazás belépési pontja.
 */
public class Main {

    /**
     * Alkalmazás belépési pontja.
     * @param args parancsori argumentumok, amiben az indulási pálya leírásának elérési útvonalát kapja meg.
     */
    public static void main(String[] args){
        try {
            Loader loader = new Loader();
            Controller controller = new Controller(loader.loadGame(args[0]));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
