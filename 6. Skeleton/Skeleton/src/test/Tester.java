package test;

import model.Game;
import model.Virologist;
import model.codes.GeneticCode;
import model.codes.BlockCode;
import model.codes.ChoreaCode;
import model.codes.ForgetCode;
import model.codes.StunCode;
import model.equipments.Bag;
import model.equipments.Cloak;
import model.map.Field;
import model.map.Laboratory;
import model.map.Shelter;
import model.map.Warehouse;
import model.strategy.*;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Tesztelést vezérlő osztály, ebben van megvalósítva a teszteseteket kezelő menürendszer
 * és a teszteseteket végrehajtó függvények, továbbá a tesztekhez szükséges
 * függvnényneveket kiíró segédfüggvények.
 */
public class Tester {

    /**
     * indentálási szint
     */
    private static int indention = 0;

    /*
     * Technically this will work...
     *
     * String name = new Object(){}.getClass().getEnclosingMethod().getName();
     * However, a new anonymous inner class will be created during compile time
     * (e.g. YourClass$1.class). So this will create a .class file for each method that deploys this trick.
     * Additionally, an otherwise unused object instance is created on each invocation during runtime.
     * So this may be an acceptable debug trick, but it does come with significant overhead.
     *
     * An advantage of this trick is that getEnclosingMethod() returns java.lang.reflect.Method which can
     * be used to retrieve all other information of the method including annotations and parameter names.
     * This makes it possible to distinguish between specific methods with the same name (method overload).
     *
     * Note that according to the JavaDoc of getEnclosingMethod() this trick should not throw a SecurityException
     * as inner classes should be loaded using the same class loader.
     * So there is no need to check the access conditions even if a security manager is present.
     *
     * Please be aware: It is required to use getEnclosingConstructor() for constructors.
     * During blocks outside of (named) methods, getEnclosingMethod() returns null
     */

    /**
     * Tesztelés során az aktuálisan meghívott függvény nevét és osztályát írja ki
     * @param m a hívott függvény reflekciója
     */
    public static void methodStart(Method m){
        for(int i = 0; i < indention; i++)
            System.out.print("\t");
        System.out.println("=>" + m.getDeclaringClass()+"::"+m.getName()+"()");
        indention++;
    }

    /**
     * Tesztelés során az aktuálisan visszatérő függvény nevét és osztályát írja ki
     * @param m a visszatérő függvény reflekciója
     */
    public static void methodEnd(Method m){
        indention--;
        for(int i = 0; i < indention; i++)
            System.out.print("\t");
        System.out.println("<=" + m.getDeclaringClass()+"::"+m.getName()+"()");
    }

    /**
     * Tesztelés során az aktuálisan meghívott konstruktor függvény nevét írja ki indentálás nélkül
     * @param m a hívott függvény reflekciója
     */
    public static void ctrMethodStart(Constructor<?> m){
        System.out.println("Ctor: " + m.getName()+"()");
    }

    /**
     * UserInput egységes kezelésére használandó függvény.
     * @param message A kiírandó üzenet. Ajánlott benne megfogalmazni az elvárt bemenet formátumát.
     * @param trueMatch A bemenet, ami esetén igazzal tér majd vissza a függvény.
     * @return A felhasználói bemenet megegyezett-e trueMatch-el.
     */
    public static boolean getUserInput(String message, String trueMatch)
    {
        System.out.print(message);
        return sc.nextLine().equals(trueMatch);
        //sc.close(); Nem szabad bezárni, mert még máshol kellhet!
    }

    /**
     * 5.1.2.1-es teszteset
     * Lebénult virológus megpróbál Aminosavat lopni egy másiktól,
     * de mivel béna, ezért nem tud.
     */
    @SkeletonTestCase(name = "Stunned Virologist tries to loot AminoAcid", id = "5.1.2.1")
    public static void test1(){
        Virologist v1 = new Virologist();
        NoLoot nl = new NoLoot();
        Virologist v2 = new Virologist();
        v1.SetLootStr(nl);

        v1.LootAminoAcidFrom(v2);
    }

    /**
     * 5.1.2.2-es teszteset
     * Egy virológus megpróbál egy másikat kirabolni (felszerelést lopni tőle), de mivel az nem bénult,
     * ezért nem lesz sikeres.
     * Ha a virológusnál 3 felszerelés van, akkor se lesz sikeres, de akkor az 5.1.2.3.-as teszteset
     * fog végrehajtódni.
     */
    @SkeletonTestCase(name = "Virologist tries to loot other Virologist who is not stunned", id = "5.1.2.2")
    public static void test2(){
        Virologist v1 = new Virologist();
        Virologist v2 = new Virologist();
        Bag b = new Bag();
        v2.AddEquipment(b);
        v1.LootEquipmentFrom(v2);
    }

    /**
     * 5.1.2.4-es teszteset
     * Egy virológus nukleotidot lop egy másik, lebénult virológustól. Aminosav lopás is ugyanígy nézne ki.
     * Akkor fog a várt eredménnyel lefutni, ha a virológusnak van még hátra akciója.
     */
    @SkeletonTestCase(name = "Virologist loots Nukleotide from other Virologist", id = "5.1.2.4")
    public static void test4(){
        Virologist v1 = new Virologist();
        Virologist v2 = new Virologist();
        Looted ltd = new Looted();
        v2.SetLootedStr(ltd);
        v2.AddNucleotide(20);

        v1.LootNucleotideFrom(v2);
    }

    /**
     * 5.1.2.5-ös teszteset
     * Egy virológus egy másik, bénult virológustól szeretne felszerelést lopni, de annak nincs.
     * Akkor fog a várt eredménnyel lefutni, ha a virológusnak van még hátra akciója és van helye
     * plusz felszerelésre.
     */
    @SkeletonTestCase(name = "Virologist tries to loot equipment from other Virologist who has none", id = "5.1.2.5")
    public static void test5(){
        Virologist v1 = new Virologist();
        Virologist v2 = new Virologist();
        Looted lt = new Looted();
        v2.SetLootedStr(lt);

        v1.LootEquipmentFrom(v2);
    }

    /**
     * 5.1.2.6-os teszteset
     * A virológus sikeresen lop egy köpenyt egy másik, bénult virológustól.
     * Akkor fog a várt eredménnyel lefutni, ha a virológusnak van még hátra akciója és van helye
     * plusz felszerelésre.
     */
    @SkeletonTestCase(name = "Virologist tries to loot equipment from other Virologist who has a cloak", id = "5.1.2.6")
    public static void test6(){
        Virologist v1 = new Virologist();
        Virologist v2 = new Virologist();
        Looted lt = new Looted();
        v2.SetLootedStr(lt);
        Cloak c = new Cloak();
        v2.AddEquipment(c);

        v1.LootEquipmentFrom(v2);
    }

    /**
     * 5.1.2.7-es teszteset
     * A virológus mozog egy megadott mezőre.
     * Akkor fog a várt eredménnyel lefutni a teszt, hogyha van még rendelkezésre álló akciója
     * a virológusnak, különben visszatér a move(Field: f) függvényből.
     */
    @SkeletonTestCase(name = "Virologist moves to field successfully", id = "5.1.2.7")
    public static void test7(){
        Field field = new Field();
        Virologist v1 = new Virologist();
        field.AddVirologist(v1);
        Field f = new Field();

        v1.Move(f);
    }

    /**
     * 5.1.2.8-as teszteset
     * Azt a folyamatot szimulálja, mikor egy virológus megakadályozódik abban,
     * hogy átlépjen egy mezőről egy másik, szomszédos mezőre. Persze csak akkor, ha még van rendelkezésre álló akciója.
     */
    @SkeletonTestCase(name = "Virologist is prevented from moving to field", id = "5.1.2.8")
    public static void test8(){
        Field field = new Field();
        Virologist v = new Virologist();
        field.AddVirologist(v);
        IMoveStr s = new NoMove();
        v.SetMoveStr(s);

        v.Move(new Field());
    }

    /**
     * 5.1.2.9-es teszteset
     * Azt a folyamatot szimulálja, mikor egy virológus sikeresen felvesz egy felszerelést az adott mezőről,
     * feltéve ha van még háta akciója.
     */
    @SkeletonTestCase(name = "Virologist picks up equipment from field successfully", id = "5.1.2.9")
    public static void test9(){
        Virologist v = new Virologist();
        Field field = new Field();
        field.AddVirologist(v);
        Bag e = new Bag();
        field.Drop(e);

        v.Equip();
    }

    /**
     * 5.1.2.10-es teszteset
     * Azt a folyamatot szimulálja, mikor egy virológus megpróbál egy felszerelést felvenni az adott mezőről,
     * de a mezőn nincsenek felszerelések.
     * Csak akkor próbálkozik, ha van mág hátra akciója.
     */
    @SkeletonTestCase(name = "Virologist tries to pick up equipment from field but there are none", id = "5.1.2.10")
    public static void test10(){
        Virologist v = new Virologist();
        Field field = new Field();
        field.AddVirologist(v);

        v.Equip();
    }

    /**
     * 5.1.2.11-es teszteset
     * Azt a folyamatot szimulálja, mikor egy virológus sikeresen felvesz egy felszerelést egy óvóhely,
     * feltéve ha van még akciója.
     */
    @SkeletonTestCase(name = "Virologist picks up equipment from shelter", id = "5.1.2.11")
    public static void test11(){
        Virologist v = new Virologist();
        Bag e = new Bag();
        Shelter field = new Shelter(e);
        field.AddVirologist(v);
        field.Drop(e);
        v.Equip();
    }

    /**
     * 5.1.2.12-es teszteset
     * Azt a folyamatot szimulálja, mikor egy virológus megpróbál egy felszerelést felvenni egy óvóhelyről,
     * de az óvóhelyen nincsenek felszerelések.
     * Egyáltalán csak akkor próbálja meg felvenni, ha van akciója.
     */
    @SkeletonTestCase(name = "Virologist tries to pick up equipment from shelter but shelter has none", id = "5.1.2.12")
    public static void test12(){
        Virologist v = new Virologist();
        Shelter field = new Shelter(null);
        field.AddVirologist(v);

        v.Equip();
    }

    /**
     * 5.1.2.13-as teszteset
     * Azt a folyamatot szimulálja, mikor egy virológus megakadályozódik abban,
     * hogy felvegyen egy felszerelést az adott mezőről.
     * Egyáltalán csak akkor próbálja meg, ha van akciója.
     */
    @SkeletonTestCase(name = "Virologist is prevented from picking up equipment from field ", id = "5.1.2.13")
    public static void test13(){
        Virologist v = new Virologist();
        NoEquip s = new NoEquip();
        v.SetEquipStr(s);

        v.Equip();
    }

    /**
     * 5.1.2.14-es teszteset
     * 5.1.2.15-ös teszteset
     * A virológus megpróbál megkenni egy másik virológust, de a virológus nem képes rá.
     * Csak akkor próbálkozik, ha van még hátra akciója.
     */
    @SkeletonTestCase(name = "Virologist tries to inject an other, but prevented", id = "5.1.2.14")
    public static void test14(){
        Virologist v = new Virologist();
        NoInject injectStr = new NoInject();
        BlockCode bCode = new BlockCode();
        Virologist target = new Virologist();
        v.AddGeneticCode(bCode);
        v.SetInjectStr(injectStr);

        v.Inject(target, bCode);
    }

    /**
     * 5.1.2.16-os teszteset
     * A virolúgus sikeresen megken egy másik virológust bénító ágenssel.
     * Csak akkor próbálkozik, ha van még hátra akciója.
     */
    @SkeletonTestCase(name = "Virologist injects other virologist with stun agent succesfully", id = "5.1.2.16")
    public static void test16(){
        Virologist injecter = new Virologist();
        StunCode sCode = new StunCode();
        Virologist target = new Virologist();
        injecter.AddGeneticCode(sCode);

        injecter.Inject(target, sCode);
    }

    /**
     * 5.1.2.17-es teszteset
     * A virolúgus sikertelenül megken egy másik virológust bénító ágenssel.
     * Csak akkor próbálkozik, ha van még hátra akciója.
     */
    @SkeletonTestCase(name = "Virologist injects other virologist with stun agent unsuccessfully", id = "5.1.2.17")
    public static void test17(){
        Virologist injecter = new Virologist();
        StunCode sCode = new StunCode();
        Virologist target = new Virologist();
        NoInjected injectedStr = new NoInjected();
        injecter.AddGeneticCode(sCode);
        target.SetInjectedStr(injectedStr);

        injecter.Inject(target, sCode);
    }

    /**
     * 5.1.2.18-es teszteset
     * A virolúgus sikeresen megken egy másik virológust felejtő ágenssel.
     * Csak akkor próbálkozik, ha van még hátra akciója.
     */
    @SkeletonTestCase(name = "Virologist injects other virologist with forget agent successfully", id = "5.1.2.18")
    public static void test18(){
        Virologist injecter = new Virologist();
        ForgetCode fCode = new ForgetCode();
        Virologist target = new Virologist();
        injecter.AddNucleotide(20);
        injecter.AddAminoAcid(20);
        injecter.AddGeneticCode(fCode);

        injecter.Inject(target, fCode);
    }

    /**
     * 5.1.2.19-es teszteset
     * A virolúgus sikertelenül megken egy másik virológust felejtő ágenssel.
     * Csak akkor próbálkozik, ha van még hátra akciója.
     */
    @SkeletonTestCase(name = "Virologist injects other virologist with forget agent unsuccessfully", id = "5.1.2.19")
    public static void test19(){
        Virologist injecter = new Virologist();
        ForgetCode fCode = new ForgetCode();
        Virologist target = new Virologist();
        NoInjected injectedStr = new NoInjected();
        injecter.AddGeneticCode(fCode);
        target.SetInjectedStr(injectedStr);
        injecter.AddNucleotide(20);
        injecter.AddAminoAcid(20);

        injecter.Inject(target, fCode);
    }

    /**
     * 5.1.2.20-es teszteset
     * A virolúgus sikeresen megken egy másik virológust blokkoló ágenssel.
     * Csak akkor próbálkozik, ha van még hátra akciója.
     */
    @SkeletonTestCase(name = "Virologist injects other virologist with block agent successfully", id = "5.1.2.20")
    public static void test20(){
        Virologist injecter = new Virologist();
        BlockCode bCode = new BlockCode();
        Virologist target = new Virologist();
        injecter.AddGeneticCode(bCode);
        injecter.AddNucleotide(20);
        injecter.AddAminoAcid(20);

        injecter.Inject(target, bCode);
    }

    /**
     * 5.1.2.21-es teszteset
     * Azt a folyamatot szimulálja, mikor egy virológus sikertelenül ken meg egy másik virológust blokkoló ágenssel,
     * mivel az ki tudja védeni a kenéseket.
     */
    @SkeletonTestCase(name = "Virologist injects other virologist with block agent unsuccessfully", id = "5.1.2.21")
    public static void test21(){
        Virologist injecter = new Virologist();
        BlockCode bCode = new BlockCode();
        Virologist target = new Virologist();
        NoInjected injectedStr = new NoInjected();
        injecter.AddGeneticCode(bCode);
        target.SetInjectedStr(injectedStr);
        injecter.AddNucleotide(20);
        injecter.AddAminoAcid(20);

        injecter.Inject(target, bCode);
    }

    /**
     * 5.4.2.22-es teszteset
     * A virológus sikeresen megken egy virológust vitustánccal.
     */
    @SkeletonTestCase(name = "Virologist injects other virologist with chorea agent successfully", id = "5.1.2.22")
    public static void test22(){
        Virologist v = new Virologist();
        ChoreaCode code = new ChoreaCode();
        Virologist target = new Virologist();
        v.AddGeneticCode(code);

        v.Inject(target, code);
    }

    /**
     * 5.4.23-es teszteset
     * A virológus próbál megkenni egy virológust vitustánccal, de a célpont stratégiája nem engedi
     */
    @SkeletonTestCase(name = "Virologist injects other virologist with chorea agent unsuccessfully", id = "5.1.2.23")
    public static void test23(){
        Virologist v = new Virologist();
        ChoreaCode code = new ChoreaCode();
        v.AddGeneticCode(code);
        Virologist target = new Virologist();
        NoInjected iStr = new NoInjected();
        target.SetInjectedStr(iStr);

        v.Inject(target, code);
    }

    /**
     * 5.4.24-es teszteset
     * Kör vége és játék vége
     */
    @SkeletonTestCase(name = "End Turn / End Game", id = "5.1.2.24")
    public static void test24(){
        Game g = new Game();
        Virologist v = new Virologist();
        g.AddVirologist(v);
        v.AddGame(g);
        ChoreaCode c = new ChoreaCode();
        BlockCode b = new BlockCode();
        ForgetCode f = new ForgetCode();
        StunCode s = new StunCode();
        v.AddGeneticCode(c);
        v.AddGeneticCode(b);
        v.AddGeneticCode(f);
        v.AddGeneticCode(s);

        v.EndTurn();
    }

    /**
     * 5.4.25-ös teszteset
     * Kör vége és játék vége nélkül
     */
    @SkeletonTestCase(name = "End Turn", id = "5.1.2.25")
    public static void test25(){
        Game g = new Game();
        Virologist v = new Virologist();
        g.AddVirologist(v);
        v.AddGame(g);
        BlockCode b = new BlockCode();
        v.AddGeneticCode(b);

        v.EndTurn();
    }

    /**
     * 5.4.26-os teszteset
     * Sikeres eldobás
     */
    @SkeletonTestCase(name = "Default drop", id = "5.1.2.26")
    public static void test26(){
        Virologist v = new Virologist();
        Field f = new Field();
        f.AddVirologist(v);
        Cloak c = new Cloak();
        v.AddEquipment(c);

        v.Drop();
    }

    /**
     * 5.4.28-as teszteset
     * Eldobás felszerelés nélkül
     */
    @SkeletonTestCase(name = "Default drop without equipment", id = "5.1.2.28")
    public static void test28(){
        Virologist v = new Virologist();
        Field f = new Field();
        f.AddVirologist(v);

        v.Drop();
    }

    /**
     * 5.4.29-es teszteset
     * Sikertelen eldobás stratégia miatt
     */
    @SkeletonTestCase(name = "Can’t drop due to strategy", id = "5.1.2.29")
    public static void test29(){
        Virologist v = new Virologist();
        Field f = new Field();
        f.AddVirologist(v);
        NoDrop d = new NoDrop();
        Bag b = new Bag();
        v.AddEquipment(b);
        v.SetDropStr(d);

        v.Drop();
    }

    /**
     * Ez az a teszt eset amikor, megpróbálunk megtanulni egy ágenst és az sikeres is.
     */
    @SkeletonTestCase(name = "Learn Agent success", id = "5.1.2.30")
    public static void test30(){
        Virologist v = new Virologist();
        ChoreaCode cc = new ChoreaCode();
        Laboratory l = new Laboratory(cc);
        l.AddVirologist(v);
        DefLearn dl = new DefLearn();
        v.SetLearnStr(dl);
        v.Learn();

    }

    /**
     *  Ez az a teszt eset amikor, megpróbálunk megtanulni egy ágenst de az nem sikerül.
     */
    @SkeletonTestCase(name = "Learn agent fail", id = "5.1.2.31")
    public static void test31(){
        Virologist v = new Virologist();
        ChoreaCode cc = new ChoreaCode();
        Laboratory l = new Laboratory(cc);
        l.AddVirologist(v);
        NoLearn nl = new NoLearn();
        v.SetLearnStr(nl);
        v.Learn();
    }

    /**
     * Ez az a teszt eset amikor egy shelter típusú mezőről anyagot veszünk fel sikeresen.
     */
    @SkeletonTestCase(name = "Collect material from field success", id = "5.1.2.32")
    public static void test32(){
        Virologist v = new Virologist();
        Warehouse wh = new Warehouse();
        wh.AddVirologist(v);
        DefCollect dc = new DefCollect();
        v.SetCollectStr(dc);
        v.Collect();

    }

    /**
     * Ez az a teszt eset amikor egy shelter típusú mezőről anyagot próbálunk fel venni de ez nem sikerül.
     */
    @SkeletonTestCase(name = "Collect material from field fail", id = "5.1.2.33")
    public static void test33(){
        Virologist v = new Virologist();
        Warehouse wh = new Warehouse();
        wh.AddVirologist(v);
        DefCollect dc = new DefCollect();
        v.SetCollectStr(dc);
        v.Collect();
    }


    /**
     * A teszteseteket tartalmazo Map
     */
    private static LinkedHashMap<String, Method> tests = new LinkedHashMap<>();

    /**
     * Beolvasásért felelős objektum
     */
    private static Scanner sc = new Scanner(System.in);

    /**
     * A menü kiíratásáért felelős osztály.
     */
    public void menu(){
        System.out.println("Menu:");
        System.out.println("Az adott tesztesetek eleresehez a kulcsszavakat kell begepelni! (kilepeshez: exit)");
        System.out.println("------------------------");
        System.out.println("Kulcsszo\t\tElnevezes");

        for (Map.Entry<String, Method> set : tests.entrySet()){
            System.out.format("%-15s\t%-20s\n", set.getKey(), set.getValue().getAnnotation(SkeletonTestCase.class).name());
        }
    }

    /**
     * A tesztesetek inicializálását végzi.
     * Kigyűjti a SkeletonTestCase annotacioval ellatott publikus metodusokat,
     * Sorba rendezi őket (ennek szabalyait a MethodComparator-banlehet megtekinteni)
     */
    public Tester() throws Exception{
        try{
            ArrayList<Method> l = new ArrayList<>();
            for (Method method : Tester.class.getMethods()){
                if (method.isAnnotationPresent(SkeletonTestCase.class)){
                    l.add(method);
                }
            }
            l.sort(new MethodComparator()); //og java with no lambda

            for (Method m : l){
                tests.put(m.getAnnotation(SkeletonTestCase.class).id(), m);
            }
        } catch (Exception e){
            throw new Exception("Error parsing TestCases... hint: " + e.getMessage());
        }
    }

    /**
     * Tesztesetet futtat, nem megfelelő inputot jelzi a felhasználó felé.
     * @param id A teszteset id-ja. (SkeletonTestCase annotációval adható meg)
     */
    private void runTest(String id) {
        Method m = tests.get(id);
        try {
            m.invoke(this); //kezelődik a null dereferálás, invoke exception-ök is.
        } catch (Exception e){
            System.out.println("Nem megfelelo input, valasszon a kulcsszavak kozul!");
        }
    }

    /**
     * A fő eseményhurkot reprezentálja.
     * Speciális parancsok:
     * "exit": kilépés
     * @throws Exception Ha megoldhatatlan hiba adódik a futás során jelzi a felhasználó felé.
     */
    public void run() throws Exception {
        try {
            menu();
            String input;
            while (!(input = sc.nextLine()).equals("exit")){
                runTest(input);
                System.out.print("Press any key to continue...");
                System.in.read();
                menu();
            }
            sc.close();
        } catch (Exception e){
            throw new Exception("An error occured while running Tester: " + e.getMessage());
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
            Tester tester = new Tester();
            tester.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
