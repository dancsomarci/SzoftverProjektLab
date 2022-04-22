package control;

//TODO TODO-k (Ne töröld ki őket!)
//TODO-------------------------------
//TODO 1) Virologist::Move() -nál most a mező default neighbourjára lép, aki a currentField parancsnál
//        a Neighbour: listában az első helyen áll!, ez nincs dokumentálva sehol, ha jól emlékszem!
//          -Vencel: szeintem beleírtam a bementi nyelv leírásánál, hogy a 0.szomszédra megy, de ez det. eset csak
//          -Marci: Én nem találom, de lehet csak vak vagyok.
//          -Vencel: elküldtem a screent messen
//          -Marci: most már látom:D, de amúgy az még hiányzik nekem, hogy a currentField meg ebben a sorrendben írja ki
//          -Vencel: Ezt dokumentáltuk korábban, hogy a CurrentField mit s hogy ír ki?
//TODO 2) +komplex tesztesetek amit kért Goldi (vagy 9 azt hiszem) Vencel:
//          -Vencel: nem 3, amin 3 jatekos van es 3at lepnek legalabb? Vagy hogy volt ez?
//          -Marci: Én 9-re emlékszem, de igazából mind1 kap 9 olyat, ami után megnyalja mind a 10 ujját!
//          -Vencel: XDDDD, mondjuk eddig is van 5 komplexebbünk, nem nagyon komplex, h 3 x 3 x 3, de komplex, úgyhogy lehet elég ha mindenki egyet ír majd pluszba
//TODO 3) Öngyilkosságnál tároló indexeléssel - aktuális játékos kezeléssel problémák


//TODO DONE + változtatások lényege
//TODO ----------------------------
//TODO 1) Ha nincs nálunk genetikai kód, és inject parancs jön [done]
            //Nem ír ki semmit!, erről elfelejtettem írni a kimeneti nyelvnél
            //Nem hiszem hogy ezt szükséges lenne dokumentálni, de elfogadok ellenérveket is!
            //Vencel: ok
//TODO 2) inject foglalkozzon azzal is hha nincs senki az adott mezon [done]
            //Az inject, attack -nál lehet saját magadra is kenni/csapni
            //A többi interakciós parancsnál csak másokra
            //Miért így?, mert így nem kell módosítani sehol!, szóval persze lehetne szebb is de hidd el így a legegyszerűbb!
            //Vencel: fullosch
            //Új fv-ek jöttek be: Controller::ChooseTarget - v nincs benne; Controller::ChooseNeighbour - v is benne van
//TODO 3) medve virus ne szaporodjon mint egy virus tenyleg, mmint 6szor hozzáadódott a virologushoz [done]
            //-> Ha egy olyat, adunk hozzá, ami már fent van a virológuson, akkor újra fel kell kerüljön, és a ttl, a nagyobbik ttl kell legyen.
            //Ehhez kellett változás itt: newFuncs: Agent::equals, Agent::setTtl, Agent::getTtl + oldFuncs: Virologist::AddAgent
            //Vencel: király
//TODO 4) hogy kulonboztetjuk meg a genetikai kodokat? [done, but not fully tested]
            //lásd alább
//TODO 4.5) Agent honnan kapja meg a ttl-t? [done, but not fully tested]
            //singleton-hoz hasonló mintát fognak követni a genetikai kódok
            //Minden kódból 1 darab objektum lesz, amit a game fog számon tartani
            //game::AddGeneticCode(GeneticCode gc); ez visszaadja az általa számon tartott genetikai kód típust! (Ezzel kell inicializálni a labort)
            //A genetikai kód felülírj az object::equals()-t className-re komparál.
            //
            //Vencel: hello tipusellenorzes, de no para, felolem ok
            //
            //Ha egy virológus megtanul egy kódot, a labor csak a saját referenciáját fogja átadni a virológusnak.
            //Tehát minden virológusnál egy olyan referencia lesz genetikai kódokból, amik a game-ben lévőkre mutatnak.
            //Ha újabb virológus lép a játékba, akkor a game updateli a genetikai kódok ttl-jét, így azok a generált ágenseknek már a megváltozott köridőket tudják átadni.
            //Változtatások/újdonságok kód szintjén:
            //-GeneticCode::equals, Game::AddAgent, GeneticCode::increment-, ::decrementPlayerCount
            //-GeneticCode::Create már olyan ágenst ad vissza, aminek a ttl-je = ttl*playerCount
//TODO 6) GeneticCode::Create implementáció mégis jó [done]
            //A RemoveAnyag() fv hívásoknak a try-on belül kell lenniük -nem mindnek-
            //a catch-ben nem kell hozzáadni a price-ot, mert a remove úgy van megírva hogy nem von le, ha exception van!, kivéve ha volt elég az egyik anyagból, s a másikból nem
//TODO 7) randomMove most működik, ha nincs elég action? [done]
            //Működik, mert a virológus köre végén resetelődik az actionCount
            //Viszont ha rákenik, és lelépi a 3-mat, akkor a kövi körben nem fog tudni semmit csinálni, de azt hiszem ez így is volt tervezve! Vencel: pontosan
//TODO 8) Újrakezdés nem reseteli a virológus köröket [done]
            //Game::NewGame fv-e reseteli a playerPointer-t is
//TODO 9) collect nem működik ha copy-zvan van a bemenet [done]
            //megoldás: Ugyanazt a Scanner referenciát kell használni a Warehouse-nak, mint a Controller-nek
            //Új osztály: SingletonScanner -> lehet később még a Controller része lesz, de egyenlőre marad így
//TODO 10) Agent parancs mukodese a palyabeolvasasnal [szerintem ez működik] - Vencel: oke, megtryolom
            //Agent parancsba kell a ttl-t is megadni! (lásd doku)

//TODO OPTIONAL nice to have changes (csak ha van idő és kedv a végén) - Vencel: LOL
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

/**
 * Prototípus külvilággal való kommunikációjáért felelős osztály.
 * Megvalósítja a dokumentációban leírt bemeneti nyelv funkcióit, valamint közvetít a modell és a felhasználó(k) között.
 */
public class Controller {
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

    /**
     * Név alapján példányosít egy osztályt, a default ctor-jával, ellenkező esetben kivétel dobódik.
     * @param className Az példányosítandó osztály "hosszú" - teljes neve
     * @return A példányosított osztály
     * @throws Exception Hiba a példányosítás során
     */
    public Object createObject(String className) throws Exception{
        Class c= Class.forName(className);
        return c.getDeclaredConstructor().newInstance();
    }

    /**
     * Az aktuálisan játszott játék.
     */
    private Game game = Game.Create();

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
        v.Attack(target);
        System.out.println(v.getName() + " attacking " + target.getName());
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
