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
                if (m != null){
                    m.invoke(this, new Object[] {Arrays.copyOfRange(inputArr, 1, inputArr.length)});
                }
                else{
                    if (!input.equals("")) //ilyenkor csak lineskip
                        System.out.println("Unknown command!");
                }
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

    public Object createObject(String className) throws Exception{
        Class c= Class.forName(className);
        return c.getDeclaredConstructor().newInstance();
    }


    private Game game = new Game();
    private HashMap<String, Field> fields = new HashMap<>();

    /*pályaleíró nyelv*/

    @ProtoInput(name="Field")
    public void Field(String[] params) throws Exception {
        try{
            if (game == null) throw new Exception();

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
                    case "InfectedLaboratory":
                        GeneticCode c = (GeneticCode) createObject("model.codes." + arg);
                        game.AddGeneticCode(c);
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
            String eqType = options.get("Equipment");
            if (eqType != null){
                f.Drop((Equipment) createObject("model.equipments." + eqType));
            }
            if (options.get("Name") == null) throw new Exception(); //Name is mandatory!
            if (fields.get(options.get("Name")) != null) throw new Exception(); //Field with Name already Exists!
            f.setName(options.get("Name"));
            fields.put(options.get("Name"), f);
            game.AddField(f);
        } catch (Exception e){
            throw new Exception("Error in Field command format!");
        }
    }

    @ProtoInput(name="Neighbours")
    public void Neighbours(String[] params) throws Exception {
        try{
            if (game == null) throw new Exception();
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
            if (game == null) throw new Exception();
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
                    case "ActionCount":
                        v.SetActionCount(Integer.parseInt(command[1]));
                        break;
                    default:
                        throw new Exception();
                }
            }
            fields.get(startingPos).AddVirologist(v);
            game.AddVirologist(v);
        } catch (Exception e){
            throw new Exception("Error in Virologist command format!");
        }
    }

    /*vége*/

    @ProtoInput(name="wau")
    public void wau(String[] params){
        game.NewGame();
    }

    @ProtoInput(name="move")
    public void move(String[] params){
        Virologist currentPlayer = game.GetCurrentPlayer();
        Field currentField = currentPlayer.getField();
        ArrayList<Field> options = currentField.GetNeighbours();
        if (options.size() > 0){
            for (int i = 0; i < options.size(); i++){
                System.out.println(i + " - " + options.get(i).getName());
            }
            Field target = options.get(sc.nextInt());
            currentPlayer.Move(target);
            System.out.println(currentPlayer.getName() + " tries moving from " + currentField.getName() + " to " + target.getName());
            //ha random mozog/nincs action stb.. nem oda fog menni feltétlen!!!
        }
    }

    @ProtoInput(name="learn")
    public void learn(String[] params){
        Virologist currentPlayer = game.GetCurrentPlayer();
        currentPlayer.Learn();
        System.out.println(currentPlayer.getName() + " tries to learn on field named " + currentPlayer.getField().getName());
    }

    @ProtoInput(name="collect")
    public void collect(String[] params){
        Virologist currentPlayer = game.GetCurrentPlayer();
        currentPlayer.Collect();
        System.out.println(currentPlayer.getName() + " tries to collect material on field named " + currentPlayer.getField().getName()); //determinisztikus eset még nincs meg!
    }

    @ProtoInput(name="inject")
    public void inject(String[] params){
        Virologist player = game.GetCurrentPlayer();
        ArrayList<GeneticCode> codes = player.getGeneticCodes();
        if (codes.size() > 0){ //mivan ha nincs neki, akkor mit írunk ki???
            int i = 0;
            for (GeneticCode code: codes) {
                System.out.println(i + ": " + code.getName());
                i++;
                System.out.println("\tCost:");
                System.out.println("\t\tNucleotide: " + code.getNucleotidePrice());
                System.out.println("\t\tAmino acid: " + code.getAminoAcidPrice());
            }
            int codeId = sc.nextInt();
            Virologist target = ChooseNeighbourOf(player);
            GeneticCode c = codes.get(codeId);
            player.Inject(target, c);
            System.out.println(player.getName() + " trying to inject " + target.getName() + " with agent created from " + c.getName());
        }
    }

    @ProtoInput(name="equip")
    public void equip(String[] params){
        Virologist player = game.GetCurrentPlayer();
        player.Equip();
        System.out.println(player.getName() + " trying to pick up equipment from field named " + player.getField().getName());
    }

    @ProtoInput(name="lootEquipment")
    public void lootEquipment(String[] params){
        Virologist player = game.GetCurrentPlayer();
        Virologist target = ChooseNeighbourOf(player);
        if (target != null){
            player.LootEquipmentFrom(target);
            System.out.println(player.getName() + " trying to loot equipment from " + target.getName());
        } else{
            System.out.println(player.getName() + " has no one to loot equipment from");
        }
    }

    @ProtoInput(name="lootAmino")
    public void lootAmino(String[] params){
        Virologist player = game.GetCurrentPlayer();
        Virologist target = ChooseNeighbourOf(player);
        if (target != null){
            player.LootAminoAcidFrom(target);
            System.out.println(player.getName() + " trying to loot amino acid from " + target.getName());
        } else{
            System.out.println(player.getName() + " has no one to loot amino acid from");
        }
    }

    @ProtoInput(name="lootNucleotide")
    public void lootNucleotide(String[] params){
        Virologist player = game.GetCurrentPlayer();
        Virologist target = ChooseNeighbourOf(player);
        if (target != null){
            player.LootNucleotideFrom(target);
            System.out.println(player.getName() + " trying to loot nucleotide from " + target.getName());
        } else{
            System.out.println(player.getName() + " has no one to loot nucleotide from");
        }
    }

    @ProtoInput(name="enemies")
    public void enemies(String[] params){
        Virologist player = game.GetCurrentPlayer();

        Field f = player.getField();
        ArrayList<Virologist> neighbours = f.GetVirologists(); //ebben saját maga is benne van, tehát nem lehet üres
        if (neighbours.size() == 1){ //player is benne van
            System.out.println(player.getName() + "has no enemies");
        } else{
            System.out.println(player.getName() + "'s enemies are:");
            for (int j = 0; j < neighbours.size(); j++){
                if (!neighbours.get(j).getName().equals(player.getName())) //Ha nem saját maga
                    System.out.println(neighbours.get(j).getName());
            }
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
        System.out.println(player.getName() + " trying to drop equipment on field named " + player.getField().getName());
    }

    @ProtoInput(name="randOn")
    public void randOn(String[] params){
        game.randOn = true;
        System.out.println("Randomized mode on");
    }

    @ProtoInput(name="randOff")
    public void randOff(String[] params){
        if (params.length > 0 && params[0].equals("Hurrikan_a_legcukibb_kutya!")) {
            game.randOn = false;
            System.out.println("Deterministic mode on!");
        } else{
            System.out.println("Access denied!");
        }
    }

    @ProtoInput(name="state")
    public void state(String[] params){
        for (Virologist v :
                game.getVirologists()) {
            v.bark();
        }
        for (Field f: game.GetFields()) {
            f.bark();
        }
    }

    @ProtoInput(name="bark")
    public void bark(String[] params){
        Virologist player = game.GetCurrentPlayer();
        player.bark();
    }

    @ProtoInput(name="attack")
    public void attack(String[] params){
        Virologist v = game.GetCurrentPlayer();
        Virologist target = ChooseNeighbourOf(v);
        if (target != null){
            v.Attack(target); //magát nem tudja fejbe csapni
            System.out.println(v.getName() + " attacking " + target.getName());
        } else{
            System.out.println(v.getName() + " has no one to attack");
        }
    }
    @ProtoInput(name="currentField")
    public void field(String[] params){
        game.GetCurrentPlayer().getField().bark();
    }

    @ProtoInput(name="map")
    public void map(String[] params){
        for (Field f: game.GetFields()) {
            f.bark();
        }
    }

    private Virologist ChooseNeighbourOf(Virologist v) {
        Field f = v.getField();
        ArrayList<Virologist> neighbours = f.GetVirologists();
        if (neighbours.size() > 1){
            for (int j = 0; j < neighbours.size(); j++){
                if (!v.getName().equals(neighbours.get(j).getName()))
                    System.out.println(j + " - " + neighbours.get(j).getName());
            }
            return neighbours.get(sc.nextInt());
        }
        return null;
    }
}
