package control;

import model.Game;

import java.lang.reflect.Method;
import java.util.*;

import model.Virologist;
import model.agents.Agent;
import model.codes.GeneticCode;
import model.equipments.Equipment;
import model.map.*;
import model.Subject;
import view.Window;

/**
 * Prototípus külvilággal való kommunikációjáért felelős osztály.
 * Megvalósítja a dokumentációban leírt bemeneti nyelv funkcióit, valamint közvetít a modell és a felhasználó(k) között.
 */
public class Controller extends Subject {

    /**
     * Az aktuálisan játszott játék.
     */
    private Game game = Game.Create();
    private Window window = new Window(this, game);;
    private String actionMessage;

    public void attack(Virologist v){
        Virologist currentPlayer = game.GetCurrentPlayer();

    }

    public void move(Field f){
        Virologist currentPlayer = game.GetCurrentPlayer();
        currentPlayer.Move(f);
        actionMessage = currentPlayer.getName() + " trying to move to " + f.getName() + "...";
    }

    public void drop(){
        Virologist currentPlayer = game.GetCurrentPlayer();
        currentPlayer.EndTurn();
        actionMessage = currentPlayer.getName() + " trying to drop an equipmnet...";
    }

    public void lootAminoFrom(Virologist v){
        Virologist currentPlayer = game.GetCurrentPlayer();
        currentPlayer.LootAminoAcidFrom(v);
        actionMessage = currentPlayer.getName() + " trying to loot amino acid form " + v.getName() + "...";
    }

    public void lootNucleoFrom(Virologist v){
        Virologist currentPlayer = game.GetCurrentPlayer();
        currentPlayer.LootNucleotideFrom(v);
        actionMessage = currentPlayer.getName() + " trying to nucleotide acid form " + v.getName() + "...";
    }

    public void lootEquipmentFrom(Virologist v){
        Virologist currentPlayer = game.GetCurrentPlayer();
        currentPlayer.LootEquipmentFrom(v);
        actionMessage = currentPlayer.getName() + " trying to equipment acid form " + v.getName() + "...";
    }

    public void collect(){
        Virologist currentPlayer = game.GetCurrentPlayer();
        currentPlayer.Collect();
        actionMessage = currentPlayer.getName() + " trying to collect material...";
    }

    public void learn(){
        Virologist currentPlayer = game.GetCurrentPlayer();
        currentPlayer.Learn();
        actionMessage = currentPlayer.getName() + " trying to learn a genetic code...";
    }

    public void equip(){
        Virologist currentPlayer = game.GetCurrentPlayer();
        currentPlayer.Equip();
        actionMessage = currentPlayer.getName() + " trying to equip an equipment...";
    }

    public void inject(Virologist v, GeneticCode code){
        Virologist currentPlayer = game.GetCurrentPlayer();
        currentPlayer.Inject(v, code);
        actionMessage = currentPlayer.getName() + " trying to inject " + v.getName() + " with " + code.getName() + "...";
    }

    public void endTurn(){
        Virologist currentPlayer = game.GetCurrentPlayer();
        currentPlayer.EndTurn();
    }

    public String getActionMessage(){
        return actionMessage;
    }

    /**
     * A bemeneti parancsokat, és a megvalósító metódusok kapcsolatát tároló objektum.
     */
    private static LinkedHashMap<String, Method> inputs = new LinkedHashMap<>();

    /**
     * Beolvasásért felelős objektum
     */
    private static Scanner sc = SingleTonScanner.Create();

    /**
     * Az osztály inicializáláskor kigíűjti a megírt parancsokhoz tartozó metódusokat,
     * valamint hibát ad, ha valami eltérés van az elvárt formátumukban, ez azonban nem jelenti, azt hogy később nem
     * várható hiba a hívásuk pillanatában.
     * @throws Exception Hiba ha nem megfelelő a formátum.
     */
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

    /**
     * Futtatja a prototípus fő eseményhurkát, ahol a parancsok beolvasásra kerülnek.
     * Az exit paranccsal lehet kilépni!
     * Ha nem értelmezhető a parancs a bemeneten, akkor az "Unknown command!" hibaüzenet íródik ki, valamint
     * Támogatja a jobb olvasás élmény érdekében, hogy üres sorokat adjunk be bemenetként, ekkor nem reagál semmit.
     * @throws Exception Ha a parancsok futtatása során hiba keletkezik kivétel dobódik.
     */
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
                    if (!"".equals(input)) //ilyenkor csak lineskip
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
     * Név alapján példányosít egy osztályt, a default ctor-jával, ellenkező esetben kivétel dobódik.
     * @param className Az példányosítandó osztály "hosszú" - teljes neve
     * @return A példányosított osztály
     * @throws Exception Hiba a példányosítás során
     */
    public Object createObject(String className) throws Exception{
        Class<?> c= Class.forName(className);
        return c.getDeclaredConstructor().newInstance();
    }

    /**
     * A pálya felépítéséhez szükséges átmeneti tároló, ami a név-mező összerendeléseket tartalmazza.
     */
    private HashMap<String, Field> fields;


    /**
     * A pályaleíró nyelv része. A Mezők leírásáért felel. (részletekért lásd dokumentáció)
     * @param params argumentumokat nem dolgoz fel jelenleg.
     * @throws Exception Jelzi, ha hiba volt a beolvasás során a szintaxisban, amit nem lehet megoldani felhasználói bevatkozás nélkül.
     */
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

    /**
     * A pályaleíró nyelv része. A Mezők szomszédsági viszonyainak leírásáért felel. (részletekért lásd dokumentáció)
     * @param params argumentumokat nem dolgoz fel jelenleg.
     * @throws Exception Jelzi, ha hiba volt a beolvasás során a szintaxisban, amit nem lehet megoldani felhasználói bevatkozás nélkül.
     */
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

    /**
     * A pályaleíró nyelv része. A Virológusok leírásáért felel. (részletekért lásd dokumentáció)
     * @param params argumentumokat nem dolgoz fel jelenleg.
     * @throws Exception Jelzi, ha hiba volt a beolvasás során a szintaxisban, amit nem lehet megoldani felhasználói bevatkozás nélkül.
     */
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
                        Class<?> c= Class.forName("model.agents." + command[1]);
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

    /**
     * A kutyaugatás felébreszti a világot. Ezzel a függvénnyel lehet inicializálni a játékot, vagy újrakezdeni.
     * A hívás után lehet elkezdeni inicializálni a pályát. (Nem mellesleg, minden wau hívás után inicializálni kell,
     * és minden játék előtt is szigorúan végre kell hajtani a parancsot, különben hiba fog csúszni a gépezetbe.)
     * @param params Jelenleg nem használ bemeneti paramétert.
     */
    @ProtoInput(name="wau")
    public void wau(String[] params){
        fields = new HashMap<>();
        game.NewGame();
    }

    /**
     * Ezzel a paranccsal léphet át játékos egy másik mezőre. Első körben kiíródnak a szomszédos mezők azonosító számai,
     * és a pályakészítésnél megadott szöveges azonosítói, amik közül választani lehet, a sorszám begépelésével.
     * @param params Jelenleg nem használ bemeneti paramétert.
     */
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

    /**
     * Ezzel a paranccsal a játékos megpróbálhat megtanulni egy genetikai kódot az aktuális mezőjének a
     * típusától függetlenül.
     * @param params Jelenleg nem használ bemeneti paramétert.
     */
    @ProtoInput(name="learn")
    public void learn(String[] params){
        Virologist currentPlayer = game.GetCurrentPlayer();
        currentPlayer.Learn();
        System.out.println(currentPlayer.getName() + " tries to learn on field named " + currentPlayer.getField().getName());
    }

    /**
     * Ezzel a paranccsal a játékos anyagot gyűjthet.
     * Determinisztikus esetben (randOff) a program megkérdezi, hogy aminosav vagy nukleotid.
     * @param params Jelenleg nem használ bemeneti paramétert.
     */
    @ProtoInput(name="collect")
    public void collect(String[] params){
        Virologist currentPlayer = game.GetCurrentPlayer();
        currentPlayer.Collect();
        System.out.println(currentPlayer.getName() + " tries to collect material on field named " + currentPlayer.getField().getName());
    }

    /**
     * Ezzel a paranccsal egy ágenst hozhat létre a virológus, a már megtanult genetikai kódok egyikének a segítségével
     * és alkalmazhatja egy virológuson. A parancs beírás után megjelenik, a megtanult kódok listája és költségeik.
     * A kiválasztást követően kiíródnak azoknak a játékosoknak az azonosítói, akiken ezt alkalmazni lehet, a játékosok
     * közül az azonosítójával lehet kiválasztani a célszemélyt.
     * @param params Jelenleg nem használ bemeneti paramétert.
     */
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

    /**
     * Ezzel a paranccsal egy felszerelés szedhető fel a mezőről.
     * Mindig a legutoljára ledobott felszerelés szedhető fel a mezőről.
     * @param params Jelenleg nem használ bemeneti paramétert.
     */
    @ProtoInput(name="equip")
    public void equip(String[] params){
        Virologist player = game.GetCurrentPlayer();
        player.Equip();
        System.out.println(player.getName() + " trying to pick up equipment from field named " + player.getField().getName());
    }

    /**
     * Ezzel a paranccsal egy virológustól lehet felszerelést ellopni.
     * A virológust a parancs kiadása után kell kiválasztani a felsoroltak közül.
     * @param params Jelenleg nem használ bemeneti paramétert.
     */
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

    /**
     * Ezzel a paranccsal lehet egy virológustól aminosavat ellopni. Kiíródnak azoknak a játékosoknak az azonosítói,
     * akiken ezt alkalmazni lehet, a játékosok közül az azonosítójával lehet kiválasztani a célszemélyt.
     * @param params Jelenleg nem használ bemeneti paramétert.
     */
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

    /**
     * Ezzel a paranccsal lehet egy virológustól nukleotidot ellopni. Kiíródnak azoknak a játékosoknak az azonosítói,
     * akiken ezt alkalmazni lehet, a játékosok közül az azonosítójával lehet kiválasztani a célszemélyt.
     * @param params Jelenleg nem használ bemeneti paramétert.
     */
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

    /**
     * Ezzel a paranccsal kilistázhatók a mezőn tartózkodó virológusok (a parancsot beírót leszámítva).
     * @param params Jelenleg nem használ bemeneti paramétert.
     */
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

    /**
     * Ezzel a paranccsal befejezhető a kör és ezáltal átadható a következő játékosnak.
     * Ha a játékos megnyeri a játékot, akkor kilép a program.
     * @param params Jelenleg nem használ bemeneti paramétert.
     */
    @ProtoInput(name="endTurn")
    public void endTurn(String[] params){
        Virologist player = game.GetCurrentPlayer();
        player.EndTurn();
        player = game.GetCurrentPlayer();
        System.out.println("The next player is: " + player.getName());
    }

    /**
     * Ezzel a paranccsal ki lehet dobni a meglévő felszerelések közül 1-et véletlenszerűen.
     * Determinisztikus mód esetén mindig az utoljára szerzettet dobjuk el.
     * @param params Jelenleg nem használ bemeneti paramétert.
     */
    @ProtoInput(name="drop")
    public void drop(String[] params){
        Virologist player = game.GetCurrentPlayer();
        player.Drop();
        System.out.println(player.getName() + " trying to drop equipment on field named " + player.getField().getName());
    }

    /**
     * Ezzel a paranccsal bekapcsolhatóak a random értékékek, a játék alap működését tükrözik, alapértelmezett indulás.
     * @param params Jelenleg nem használ bemeneti paramétert.
     */
    @ProtoInput(name="randOn")
    public void randOn(String[] params){
        game.randOn = true;
        System.out.println("Randomized mode on");
    }

    /**
     * Ezzel a paranccsal determinisztikussá tehető a játék, ez tesztelési célt szolgál kizárólag.
     * A beadott paraméterrel ellenőrizzük a jogosultságot, ugyanis csak rendszergazdák vagy a
     * dokumentációkhoz hozzá férők számára érhető el ez a funkció.
     * A bemenet paraméter helyére ezt kell írni: “Hurrikan_a_legcukibb_kutya!”.
     * @param params A titkos jelszó, ami azonosítja a felhasználót.
     */
    @ProtoInput(name="randOff")
    public void randOff(String[] params){
        if (params.length > 0 && params[0].equals("Hurrikan_a_legcukibb_kutya!")) {
            game.randOn = false;
            System.out.println("Deterministic mode on!");
        } else{
            System.out.println("Access denied!");
        }
    }

    /**
     * Ezzel a paranccsal lesz megjeleníthető a játék aktuális állapota, ami gyakorlatban azt jelenti, hogy az
     * összes játékban lévő virológus információit jeleníti meg, hasonló formátumban, mint a bark parancs.
     * @param params Jelenleg nem használ bemeneti paramétert.
     */
    @ProtoInput(name="state")
    public void state(String[] params){
        for (Virologist v :
                game.getVirologists()) {
            v.bark();
        }
    }

    /**
     * Ugatás hatására kilistázza a rendelkezésre álló aminosav és nukleotid mennyiséget, továbbá a birtokolt tárgyakat,
     * megtanult genetikai kódokat és a körben még végrehajtható interakciók számát. Valamint annak a mezőnek az
     * azonosítóját, ahol tartózkodik.
     * @param params Jelenleg nem használ bemeneti paramétert.
     */
    @ProtoInput(name="bark")
    public void bark(String[] params){
        Virologist player = game.GetCurrentPlayer();
        player.bark();
    }

    /**
     * A virológus virológusba vágja a fejszéjét. A lebaltázandó virológust a parancs kiadása után kell kiválasztani a
     * felsorolt játékosazonosítók közül.
     * @param params Jelenleg nem használ bemeneti paramétert.
     */
    @ProtoInput(name="attack") //magát is megcsaphatja
    public void attack(String[] params){
        Virologist v = game.GetCurrentPlayer();
        Virologist target = ChooseNeighbour(v);
        System.out.println(v.getName() + " attacking " + target.getName());
        v.Attack(target);
    }

    /**
     * A soron lévő játékos tartózkodási mezőjének adatait írja ki. (Mező fajátáját, nevét, az ott található ledobott
     * tárgyakat, az ott tartózkodó játékosokat, és a mezőről navigálható szomszédok neveit.
     * @param params Jelenleg nem használ bemeneti paramétert.
     */
    @ProtoInput(name="currentField")
    public void field(String[] params){
        game.GetCurrentPlayer().getField().bark();
    }

    /**
     * Gyakorlatilag a játékban lévő összes mezőre hasonló formátumban ír ki információkat, mint a currentField.
     * @param params Jelenleg nem használ bemeneti paramétert.
     */
    @ProtoInput(name="map")
    public void map(String[] params){
        for (Field f: game.GetFields()) {
            f.bark();
        }
    }

    /**
     * Az aktuálisan soron lévő játékos szomszédai közül tesz lehetővé választást a felhasználó számára.
     * A választásban nem lesz felsorolva az aktuális játékos.
     * @param v aktuális játékos
     * @return A választott virológus referenciája
     */
    private Virologist ChooseTarget(Virologist v) {
        Field f = v.getField();
        ArrayList<Virologist> neighbours = f.GetVirologists();
        if (neighbours.size() > 1){

            for (int j = 0; j < neighbours.size(); j++) {
                if (!v.getName().equals(neighbours.get(j).getName())){
                    System.out.println(j + " - " + neighbours.get(j).getName());

                }
            }
            return neighbours.get(sc.nextInt());
        }
        return null;
    }

    /**
     * Az aktuálisan soron lévő játékos szomszédai közül tesz lehetővé választást a felhasználó számára.
     * A választásban fel lesz sorolva az aktuális játékos is.
     * @param v aktuális játékos
     * @return A választott virológus referenciája
     */
    private Virologist ChooseNeighbour(Virologist v) {
        Field f = v.getField();
        ArrayList<Virologist> neighbours = f.GetVirologists();
        for (int j = 0; j < neighbours.size(); j++){
            System.out.println(j + " - " + neighbours.get(j).getName());
        }
        return neighbours.get(sc.nextInt());
    }
}
