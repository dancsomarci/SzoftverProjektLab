import control.Controller;
import control.Loader;
//TODO comment
//TODO 2 Main osztaly is van, sztem lehetne a controlban, de itt is jo akar
public class Main {

    /**
     * Alkalmazás belépési pontja.
     * @param args parancsori argumentumok.
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
