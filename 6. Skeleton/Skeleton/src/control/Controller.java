package control;

import model.Game;

import java.lang.reflect.Method;
import java.util.*;

import model.Virologist;
import model.codes.BlockCode;
import model.codes.ForgetCode;
import model.codes.GeneticCode;
import model.equipments.Bag;
import model.map.Field;
import model.map.Laboratory;
import model.map.Shelter;
import model.map.Warehouse;

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
                System.out.println("The current input was: [" + input + "]");
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

    private Game game;

    @ProtoInput(name="wau")
    public void wau(String[] params){
        game = new Game(); //bemeneti nyelvet kitalálni!
        Field f = new Field();
        Laboratory labor = new Laboratory(new ForgetCode());
        Warehouse wh = new Warehouse();
        Shelter sh = new Shelter(new Bag());

        f.AddNeighbour(labor);
        labor.AddNeighbour(f);

        f.AddNeighbour(wh);
        wh.AddNeighbour(f);

        f.AddNeighbour(sh);
        sh.AddNeighbour(f);

        Virologist v1 = new Virologist();
        Virologist v2 = new Virologist();

        v2.setName("Goldi");
        v1.setName("TanuloschGang");

        v1.AddGeneticCode(new BlockCode());

        f.AddVirologist(v1);
        f.AddVirologist(v2);
        game.AddVirologist(v1);
        game.AddVirologist(v2);

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
