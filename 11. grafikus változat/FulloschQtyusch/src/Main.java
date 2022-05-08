import control.Controller;
import control.Loader;

public class Main {
    public static void main(String[] args){
        try {
            Loader loader = new Loader();
            Controller controller = new Controller(loader.loadGame(args[0]));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
