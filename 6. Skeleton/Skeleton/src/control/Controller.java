package control;

import model.Game;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.*;

import model.Virologist;
import model.agents.Agent;
import model.codes.BlockCode;
import model.codes.ForgetCode;
import model.codes.GeneticCode;
import model.equipments.Bag;
import model.equipments.Equipment;
import model.map.*;

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
            throw e;
            //throw new Exception("An unexpected error occured... hint: " + e.getMessage());
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

    /*
    public Object createObject(String className, Object[] arguments) throws Exception{
        Class c= Class.forName(className);
        return c.getDeclaredConstructor().newInstance(arguments);
    }
    */

    public Object createObject(String className) throws Exception{
        Class c= Class.forName(className);
        return c.getDeclaredConstructor().newInstance();
    }


    private Game game;
    private HashMap<String, Field> fields = new HashMap<>();

    /*pályaleíró nyelv*/

    @ProtoInput(name="Field")
    public void Field(String[] params) throws Exception {
        try{
            HashMap<String, String> options = new HashMap<>();
            String line;
            while (!(line = sc.nextLine()).equals("end")){
                String[] command = line.split(" ");
                options.put(command[0], command[1]);
            }

            Field f = null;
            String arg = options.get("Param");

            if (arg == null){ //default ctor
                f = (Field) createObject("model.map." + options.get("Type"));
            } else{
                //csúnya, de ez van
                switch(options.get("Type")){
                    case "Laboratory":
                        GeneticCode c = (GeneticCode) createObject("model.codes." + arg);
                        f = new Laboratory(c);
                        break;
                    case "Shelter":
                        Equipment e = (Equipment) createObject("model.equipments."+arg);
                        f = new Shelter(e);
                        break;
                    default:
                        break;
                }
            }
            if (options.get("Name") == null) throw new Exception(); //Name is mandatory!
            if (fields.get(options.get("Name")) != null) throw new Exception(); //Field with Name already Exists!
            fields.put(options.get("Name"), f);
        } catch (Exception e){
            throw new Exception("Error in Field command format!");
        }
    }

    @ProtoInput(name="Neighbours")
    public void Neighbours(String[] params) throws Exception {
        try{
            String line;
            while (!(line = sc.nextLine()).equals("end")){
                String[] command = line.split(" ");
                Field f0 = fields.get(command[0]);
                Field f1 = fields.get(command[1]);
                f0.AddNeighbour(f1);
                f1.AddNeighbour(f0);
            }
        }catch(Exception e){
            throw new Exception("Error in Neighbours command format!");
        }
    }

    @ProtoInput(name="Virologist")
    public void Virologist(String[] params) throws Exception {
        try{
            String line;
            Virologist v = new Virologist();
            String startingPos = "";
            while (!(line = sc.nextLine()).equals("end")){
                String[] command = line.split(" ");
                switch(command[0]){
                    case "Name":
                        v.setName(command[1]);
                        break;
                    case "Equipment":
                        v.AddEquipment((Equipment) createObject("model.equipments." + command[1]));
                        break;
                    case "Amino":
                        v.AddAminoAcid(Integer.parseInt(command[1]));
                        break;
                    case "Nucleo":
                        v.AddNucleotide(Integer.parseInt(command[1]));
                        break;
                    case "Agent":
                        Class c= Class.forName("model.agents." + command[1]);
                        Agent a = (Agent) c.getConstructor(Integer.TYPE).newInstance(Integer.parseInt(command[2]));
                        a.Apply(v);
                        v.AddAgent(a);
                        a.ApplyStrategy(v);
                        break;
                    case "StartingPos":
                        startingPos = command[1]; //in case there are command after this that throw error we don't want to mess up the existing setup
                        break;
                    case "GeneticCode":
                        v.AddGeneticCode((GeneticCode) createObject("model.codes." + command[1]));
                        break;
                    default:
                        throw new Exception();
                }
            }
            fields.get(startingPos).AddVirologist(v);
            v.bark();
        } catch (Exception e){
            throw new Exception("Error in Virologist command format!");
        }
    }

    @ProtoInput(name="test")
    public void Test(String[] params){
        for (Map.Entry<String, Field> set :
                fields.entrySet()) {

            // Printing all elements of a Map
            System.out.println(set.getKey() + " = "
                    + set.getValue());
        }
    }

    /*vége*/



    @ProtoInput(name="wau")
    public void wau(String[] params){
        game = new Game();

        System.out.println("Initialize...");
    }

    @ProtoInput(name="move")
    public void move(String[] params){
        Virologist currentPlayer = game.GetCurrentPlayer();
        Field currentField = currentPlayer.getField();
        ArrayList<Field> options = currentField.GetNeighbours();
        for (int i = 0; i < options.size(); i++){
            System.out.println(i);
        }
        currentPlayer.Move(options.get(sc.nextInt()));
        System.out.println("Moving...");
    }

    @ProtoInput(name="learn")
    public void learn(String[] params){
        Virologist currentPlayer = game.GetCurrentPlayer();
        currentPlayer.Learn();
        System.out.println("Learning...");
    }

    @ProtoInput(name="collect")
    public void collect(String[] params){
        Virologist currentPlayer = game.GetCurrentPlayer();
        currentPlayer.Collect();
        System.out.println("Collecting..."); //determinisztikus eset még nincs meg!
    }

    @ProtoInput(name="inject")
    public void inject(String[] params){
        Virologist player = game.GetCurrentPlayer();
        ArrayList<GeneticCode> codes = player.getGeneticCodes();
        if (codes.size() > 0){ //mivan ha nincs neki, akkor mit írunk ki???
            int i = 0;
            for (GeneticCode code: codes) {
                System.out.println(i+" Code" + i++);
                System.out.println("\tCost:");
                System.out.println("\t\tNucleotide: " + code.getNucleotidePrice());
                System.out.println("\t\tAmino acid: " + code.getAminoAcidPrice());
            }
            int codeId = sc.nextInt();
            Field f = player.getField();
            ArrayList<Virologist> neighbours = f.GetVirologists(); //ebben saját maga is benne van, de persze saját magát is injectelheti!
            for (int j = 0; j < neighbours.size(); j++){
                System.out.println(j + " - " + neighbours.get(j).getName());
            }
            Virologist target = neighbours.get(sc.nextInt());
            player.Inject(target, codes.get(codeId));
            System.out.println("Injecting...");
        }
    }

    @ProtoInput(name="equip")
    public void equip(String[] params){
        Virologist player = game.GetCurrentPlayer();
        player.Equip();
        System.out.println("Picking up equipment...");
    }

    @ProtoInput(name="lootEquipment")
    public void lootEquipment(String[] params){
        Virologist player = game.GetCurrentPlayer();
        Field f = player.getField();
        ArrayList<Virologist> neighbours = f.GetVirologists(); //ebben saját maga is benne van, ami nem túl jó!
        for (int j = 0; j < neighbours.size(); j++){
            System.out.println(j + " - " + neighbours.get(j).getName());
        }
        Virologist target = neighbours.get(sc.nextInt());
        player.LootEquipmentFrom(target);
        System.out.println("Looting equipment...");
    }

    @ProtoInput(name="lootAmino")
    public void lootAmino(String[] params){
        Virologist player = game.GetCurrentPlayer();
        Field f = player.getField();
        ArrayList<Virologist> neighbours = f.GetVirologists(); //ebben saját maga is benne van, ami nem túl jó!
        for (int j = 0; j < neighbours.size(); j++){
            System.out.println(j + " - " + neighbours.get(j).getName());
        }
        Virologist target = neighbours.get(sc.nextInt());
        player.LootAminoAcidFrom(target);
        System.out.println("Looting amino acid...");
    }

    @ProtoInput(name="lootNucleotide")
    public void lootNucleotide(String[] params){
        Virologist player = game.GetCurrentPlayer();
        Field f = player.getField();
        ArrayList<Virologist> neighbours = f.GetVirologists(); //ebben saját maga is benne van, ami nem túl jó!
        for (int j = 0; j < neighbours.size(); j++){
            System.out.println(j + " - " + neighbours.get(j).getName());
        }
        Virologist target = neighbours.get(sc.nextInt());
        player.LootNucleotideFrom(target);
        System.out.println("Looting nucleotide...");
    }

    @ProtoInput(name="enemies")
    public void enemies(String[] params){
        Virologist player = game.GetCurrentPlayer();
        Field f = player.getField();
        ArrayList<Virologist> neighbours = f.GetVirologists(); //ebben saját maga is benne van, ami nem túl jó!
        for (int j = 0; j < neighbours.size(); j++){
            System.out.println(neighbours.get(j).getName());
        }
    }

    @ProtoInput(name="endTurn")
    public void endTurn(String[] params){
        Virologist player = game.GetCurrentPlayer();
        player.EndTurn();
        player = game.GetCurrentPlayer();
        System.out.println("The next player is: " + player.getName());
    }

    @ProtoInput(name="drop")
    public void drop(String[] params){
        Virologist player = game.GetCurrentPlayer();
        player.Drop();
        System.out.println("Dropping equipment...");
    }

    @ProtoInput(name="randOn")
    public void randOn(String[] params){

    }

    @ProtoInput(name="randOff")
    public void randOff(String[] params){

    }

    @ProtoInput(name="state")
    public void state(String[] params){
        for (Virologist v :
                game.getVirologists()) {
            v.bark();
            System.out.println();
        }
    }

    @ProtoInput(name="bark")
    public void bark(String[] params){
        Virologist player = game.GetCurrentPlayer();
        player.bark();
    }

    @ProtoInput(name="attack")
    public void attack(String[] params){

    }
}
