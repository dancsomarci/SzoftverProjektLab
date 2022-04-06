package control;

import model.Game;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class Controller {
    private static LinkedHashMap<String, Method> inputs = new LinkedHashMap<>();

    /**
     * Beolvasásért felelős objektum
     */
    private static Scanner sc = new Scanner(System.in);

    public Controller() throws Exception {
        try{
            for (Method method : Controller.class.getMethods()){
                if (method.isAnnotationPresent(ProtoInput.class)){
                    inputs.put(method.getAnnotation(ProtoInput.class).name(), method);
                }
            }
        } catch (Exception e){
            throw new Exception("Error parsing TestCases... hint: " + e.getMessage());
        }
    }

    public void run() throws Exception {
        try {
            String input;
            while (!(input = sc.nextLine()).equals("exit")){ //exit parancs itt kezelődik
                Method m = inputs.get(input);
                if (m != null)
                    m.invoke(this);
                else
                    System.out.println("Unknown command!");
            }
        } catch (Exception e){
            throw new Exception("An unexpected error occured... hint: " + e.getMessage());
        }
        finally{
            if (sc != null)
                sc.close();
        }
    }

    /**
     * Alkalmazás belépési pontja.
     * @param args parancsori argumentumok.
     */
    public static void main(String[] args){
        try {
            Controller controller = new Controller();
            controller.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @ProtoInput(name="wau")
    public void wau(){
        Game game = new Game();
    }

    @ProtoInput(name="move")
    public void move(){

    }

    @ProtoInput(name="learn")
    public void learn(){

    }

    @ProtoInput(name="collect")
    public void collect(){

    }

    @ProtoInput(name="inject")
    public void inject(){

    }

    @ProtoInput(name="equip")
    public void equip(){

    }

    @ProtoInput(name="lootEquipment")
    public void lootEquipment(){

    }

    @ProtoInput(name="lootAmino")
    public void lootAmino(){

    }

    @ProtoInput(name="lootNucleotide")
    public void lootNucleotide(){

    }

    @ProtoInput(name="enemies")
    public void enemies(){

    }

    @ProtoInput(name="endTurn")
    public void endTurn(){

    }

    @ProtoInput(name="drop")
    public void drop(){

    }

    @ProtoInput(name="randOn")
    public void randOn(){

    }

    @ProtoInput(name="randOff")
    public void randOff(){

    }

    @ProtoInput(name="state")
    public void state(){

    }

    @ProtoInput(name="bark")
    public void bark(){

    }

    @ProtoInput(name="attack")
    public void attack(){

    }
}
