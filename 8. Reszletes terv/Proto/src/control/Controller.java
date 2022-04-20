package control;

//TODO TODO-k (Ne töröld ki őket!)
//TODO-------------------------------
//TODO 1) Virologist::Move() -nál most a mező default neighbourjára lép, aki a currentField parancsnál
//        a Neighbour: listában az első helyen áll!, ez nincs dokumentálva sehol, ha jól emlékszem!
//TODO 2) +komplex tesztesetek amit kért Goldi (vagy 9 azt hiszem)

//TODO DONE + változtatások lényege
//TODO ----------------------------
//TODO 1) Ha nincs nálunk genetikai kód, és inject parancs jön [done]
            //Nem ír ki semmit!, erről elfelejtettem írni a kimeneti nyelvnél
            //Nem hiszem hogy ezt szükséges lenne dokumentálni, de elfogadok ellenérveket is!
//TODO 2) inject foglalkozzon azzal is hha nmincs senki az adott mezon [done]
            //Az inject, attack -nál lehet saját magadra is kenni/csapni
            //A többi interakciós parancsnál csak másokra
            //Miért így?, mert így nem kell módosítani sehol!, szóval persze lehetne szebb is de hidd el így a legegyszerűbb!
            //Új fv-ek jöttek be: Controller::ChooseTarget - v nincs benne; Controller::ChooseNeighbour - v is benne van
//TODO 3) medve virus ne szaporodjon mint egy virus tenyleg, mmint 6szor hozzáadódott a virologushoz [done]
            //-> Ha egy olyat, adunk hozzá, ami már fent van a virológuson, akkor újra fel kell kerüljön, és a ttl, a nagyobbik ttl kell legyen.
            //Ehhez kellett változás itt: newFuncs: Agent::equals, Agent::setTtl, Agent::getTtl + oldFuncs: Virologist::AddAgent
//TODO 4) hogy kulonboztetjuk meg a genetikai kodokat? [done, but not fully tested]
            //lásd alább
//TODO 4.5) Agent honnan kapja meg a ttl-t? [done, but not fully tested]
            //singleton-hoz hasonló mintát fognak követni a genetikai kódok
            //Minden kódból 1 darab objektum lesz, amit a game fog számon tartani
            //game::AddGeneticCode(GeneticCode gc); ez visszaadja az általa számon tartott genetikai kód típust! (Ezzel kell inicializálni a labort)
            //A genetikai kód felülírj az object::equals()-t className-re komparál.
            //Ha egy virológus megtanul egy kódot, a labor csak a saját referenciáját fogja átadni a virológusnak.
            //Tehát minden virológusnál egy olyan referencia lesz genetikai kódokból, amik a game-ben lévőkre mutatnak.
            //Ha újabb virológus lép a játékba, akkor a game updateli a genetikai kódok ttl-jét, így azok a generált ágenseknek már a megváltozott köridőket tudják átadni.
            //Változtatások/újdonságok kód szintjén:
            //-GeneticCode::equals, Game::AddAgent, GeneticCode::increment-, ::decrementPlayerCount
            //-GeneticCode::Create már olyan ágenst ad vissza, aminek a ttl-je = ttl*playerCount
//TODO 6) GeneticCode::Create implementáció nem jó [done]
            //A RemoveAnyag() fv hívásoknak a try-on belül kell lenniük
            //a catch-ben nem kell hozzáadni a price-ot, mert a remove úgy van megírva hogy nem von le, ha exception van!
//TODO 7) randomMove most működik, ha nincs elég action? [done]
            //Működik, mert a virológus köre végén resetelődik az actionCount
            //Viszont ha rákenik, és lelépi a 3-mat, akkor a kövi körben nem fog tudni semmit csinálni, de azt hiszem ez így is volt tervezve!
//TODO 8) Újrakezdés nem reseteli a virológus köröket [done]
            //Game::NewGame fv-e reseteli a playerPointer-t is
//TODO 9) collect nem működik ha copy-zvan van a bemenet [done]
            //megoldás: Ugyanazt a Scanner referenciát kell használni a Warehouse-nak, mint a Controller-nek
            //Új osztály: SingletonScanner -> lehet később még a Controller része lesz, de egyenlőre marad így

//TODO OPTIONAL nice to have changes (csak ha van idő és kedv a végén)
//TODO -----------------------------------------------------------------
//TODO Game osztályban kezelni az olyan eseteket pl.: amikor nincs player hozzáadva, de mégis endTurn (valahogy szép üzenetbe burkolni a felhazsnáló felé)
//TODO A Pálya parser exception nyelvezetét meg lehetne szépre írni, hogy egyszerűbb legyen felderíteni a hibákat.

//TODO Mellékes
//TODO --------
//TODO 10) Kommentezés
//TODO 11) BUG tesztelés
//TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO

import model.Game;

import java.lang.reflect.Method;
import java.util.*;

import model.Virologist;
import model.agents.Agent;
import model.codes.GeneticCode;
import model.equipments.Equipment;
import model.map.*;

public class Controller {
    private static LinkedHashMap<String, Method> inputs = new LinkedHashMap<>();

    /**
     * Beolvasásért felelős objektum
     */
    //private static Scanner sc = new Scanner(System.in);
    private static Scanner sc = SingleTonScanner.Create();

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


    private Game game = Game.Create(); //Csak egyszer kell hívni, mert ugyanazt az egy objektumot adja vissza mindig!
    private HashMap<String, Field> fields;

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
                        GeneticCode c1 = (GeneticCode) createObject("model.codes." + arg);
                        f = new Laboratory(game.AddGeneticCode(c1));
                        break;
                    case "InfectedLaboratory":
                        GeneticCode c2 = (GeneticCode) createObject("model.codes." + arg);
                        f = new InfectedLaboratory(game.AddGeneticCode(c2));
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
        fields = new HashMap<>();
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
        System.out.println(currentPlayer.getName() + " tries to collect material on field named " + currentPlayer.getField().getName());
    }

    @ProtoInput(name="inject")
    public void inject(String[] params){
        Virologist player = game.GetCurrentPlayer();
        ArrayList<GeneticCode> codes = player.getGeneticCodes();
        if (codes.size() > 0){
            int i = 0;
            for (GeneticCode code: codes) {
                System.out.println(i + ": " + code.getName());
                i++;
                System.out.println("\tCost:");
                System.out.println("\t\tNucleotide: " + code.getNucleotidePrice());
                System.out.println("\t\tAmino acid: " + code.getAminoAcidPrice());
            }
            int codeId = sc.nextInt();
            Virologist target = ChooseNeighbour(player);
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
        Virologist target = ChooseTarget(player);
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
        Virologist target = ChooseTarget(player);
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
        Virologist target = ChooseTarget(player);
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
            System.out.println(player.getName() + " has no enemies");
        } else{
            System.out.println(player.getName() + " 's enemies are:");
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
    }

    @ProtoInput(name="bark")
    public void bark(String[] params){
        Virologist player = game.GetCurrentPlayer();
        player.bark();
    }

    @ProtoInput(name="attack") //magát is megcsaphatja
    public void attack(String[] params){
        Virologist v = game.GetCurrentPlayer();
        Virologist target = ChooseNeighbour(v);
        v.Attack(target);
        System.out.println(v.getName() + " attacking " + target.getName());
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

    private Virologist ChooseTarget(Virologist v) { //Ebben v nincs benne! (null is jöhet belőle)
        Field f = v.getField();
        ArrayList<Virologist> neighbours = f.GetVirologists();
        if (neighbours.size() > 1){
            int i = 0;
            for (int j = 0; j < neighbours.size(); j++) {
                if (!v.getName().equals(neighbours.get(j).getName())){
                    System.out.println(i + " - " + neighbours.get(j).getName());
                    i++;
                }
            }
            return neighbours.get(sc.nextInt());
        }
        return null;
    }

    private Virologist ChooseNeighbour(Virologist v) { //Ebben v is benne van! szóval mindig értelmeset ad vissza
        Field f = v.getField();
        ArrayList<Virologist> neighbours = f.GetVirologists();
        for (int j = 0; j < neighbours.size(); j++){
            System.out.println(j + " - " + neighbours.get(j).getName());
        }
        return neighbours.get(sc.nextInt());
    }
}
