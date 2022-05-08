package control;

import model.Game;
import model.Virologist;
import model.agents.Agent;
import model.codes.GeneticCode;
import model.equipments.Equipment;
import model.map.Field;
import model.map.InfectedLaboratory;
import model.map.Laboratory;
import model.map.Shelter;

import java.io.File;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Scanner;

/**
 * Architekturálisan a controller része. A pálya fileból betöltéséért felelős osztály.
 * A file formátumát a dokumentáció bemeneti nyelv szekciója írja le részletesen.
 */
public class Loader {
    /**
     * Beolvasásért felelős objektum
     */
    Scanner sc;

    /**
     * Az aktuálisan játszott játék.
     */
    private Game game;

    /**
     * A pálya felépítéséhez szükséges átmeneti tároló, ami a név-mező összerendeléseket tartalmazza.
     */
    private HashMap<String, Field> fields;

    /**
     * A bemeneti parancsokat, és a megvalósító metódusok kapcsolatát tároló objektum.
     */
    private static LinkedHashMap<String, Method> inputs = new LinkedHashMap<>();

    /**
     * Az osztály inicializáláskor kigyűjti a bemeneti nyelv parancsaihoz tartozó metódusokat,
     * valamint hibát ad, ha valami eltérés van ezen metódusok szintaktikájában.
     * A bemeneti file formátumában lévő hibákért nem jelez!
     * @throws Exception Hiba ha nem megfelelő a metódusok formátum.
     */
    public Loader() throws Exception {
        try{
            for (Method method : Controller.class.getDeclaredMethods()){
                if (method.isAnnotationPresent(LoaderInput.class)){
                    inputs.put(method.getAnnotation(LoaderInput.class).name(), method);
                }
            }
        } catch (Exception e){
            throw new Exception("Error in Loader methods!" + e.getMessage());
        }

        fields = new HashMap<>();
        game = Game.Create();
        game.NewGame();
    }

    /**
     * Betölti a játékot, a bemeneti nyelv szintaktikáját feltételezve.
     * Az adatokat a "path" elérési útvonalon található file-ból veszi.
     * @param path A bemeneti nyelv parancsait tartalmazó file elérési útvonala.
     * @return Az inicializált játékot adja vissza.
     * @throws Exception Ha a parancsok futtatása során hiba keletkezik, vagy nem létező parancsot kap bemenetként
     * kivétel dobódik.
     */
    public Game loadGame(String path) throws Exception {
        try {
            File inputFile = new File(path);
            sc = new Scanner(inputFile);
            while (sc.hasNextLine()){
                String input = sc.nextLine();
                Method m = inputs.get(input);
                if (m != null){
                    m.invoke(this);
                }
                else{
                    if (!input.isBlank())
                        throw new Exception("Unknown command in input file!");
                }
            }
        } catch (Exception e){
            throw e;
        }
        finally{
            if (sc != null)
                sc.close();
        }
        return game;
    }

    /**
     * Név alapján példányosít egy osztályt, a default ctor-jával, ellenkező esetben kivétel dobódik.
     * @param className Az példányosítandó osztály "hosszú" - teljes neve
     * @return A példányosított osztály
     * @throws Exception Hiba a példányosítás során
     */
    private Object createObject(String className) throws Exception{
        Class<?> c= Class.forName(className);
        return c.getDeclaredConstructor().newInstance();
    }


    /**
     * A pályaleíró nyelv része. A Mezők leírásáért felel. (részletekért lásd dokumentáció)
     * @throws Exception Jelzi, ha hiba volt a beolvasás során a szintaxisban,
     * amit nem lehet megoldani felhasználói bevatkozás nélkül.
     */
    @LoaderInput(name="Field")
    private void Field() throws Exception {
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
     * @throws Exception Jelzi, ha hiba volt a beolvasás során a szintaxisban,
     * amit nem lehet megoldani felhasználói bevatkozás nélkül.
     */
    @LoaderInput(name="Neighbours")
    private void Neighbours() throws Exception {
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
     * @throws Exception Jelzi, ha hiba volt a beolvasás során a szintaxisban,
     * amit nem lehet megoldani felhasználói bevatkozás nélkül.
     */
    @LoaderInput(name="Virologist")
    private void Virologist() throws Exception {
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

}
