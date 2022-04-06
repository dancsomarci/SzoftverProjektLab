package control;

import model.Game;

import java.lang.reflect.Method;
import java.util.*;

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
                String[] inputArr = input.split(" ");
                Method m = inputs.get(inputArr[0]);
                if (m != null)
                    m.invoke(this, new Object[] {Arrays.copyOfRange(inputArr, 1, inputArr.length)});
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
    public void wau(String[] params){
    }

    @ProtoInput(name="move")
    public void move(String[] params){

    }

    @ProtoInput(name="learn")
    public void learn(String[] params){

    }

    @ProtoInput(name="collect")
    public void collect(String[] params){

    }

    @ProtoInput(name="inject")
    public void inject(String[] params){

    }

    @ProtoInput(name="equip")
    public void equip(String[] params){

    }

    @ProtoInput(name="lootEquipment")
    public void lootEquipment(String[] params){

    }

    @ProtoInput(name="lootAmino")
    public void lootAmino(String[] params){

    }

    @ProtoInput(name="lootNucleotide")
    public void lootNucleotide(String[] params){

    }

    @ProtoInput(name="enemies")
    public void enemies(String[] params){

    }

    @ProtoInput(name="endTurn")
    public void endTurn(String[] params){

    }

    @ProtoInput(name="drop")
    public void drop(String[] params){

    }

    @ProtoInput(name="randOn")
    public void randOn(String[] params){

    }

    @ProtoInput(name="randOff")
    public void randOff(String[] params){

    }

    @ProtoInput(name="state")
    public void state(String[] params){

    }

    @ProtoInput(name="bark")
    public void bark(String[] params){

    }

    @ProtoInput(name="attack")
    public void attack(String[] params){

    }
}
