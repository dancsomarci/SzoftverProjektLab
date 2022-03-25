package test;

import model.Virologist;
import model.codes.BlockCode;
import model.equipments.Bag;
import model.equipments.Cloak;
import model.map.Field;
import model.map.Shelter;
import model.strategy.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Scanner;
import java.util.function.Function;

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
            System.out.print("/t");
        System.out.println(m.getDeclaringClass()+"::"+m.getName()+"()");
        indention++;
    }

    /**
     * Tesztelés során az aktuálisan visszatérő függvény nevét és osztályát írja ki
     * @param m a visszatérő függvény reflekciója
     */
    public static void methodEnd(Method m){
        indention--;
        for(int i = 0; i < indention; i++)
            System.out.print("/t");
        System.out.println(m.getDeclaringClass()+"::"+m.getName()+"()");
    }

    /**
     * Tesztelés során az aktuálisan meghívott konstruktor függvény nevét írja ki indentálás nélkül
     * @param m a hívott függvény reflekciója
     */
    public static void ctrMethodStart(Constructor<?> m){
        System.out.println(m.getName()+"()");
    }

    /**
     * UserInput egységes kezelésére használandó függvény.
     * @param message A kiírandó üzenet. Ajánlott benne megfogalmazni az elvárt bemenet formátumát.
     * @param trueMatch A bemenet, ami esetén igazzal tér majd vissza a függvény.
     * @return A felhasználói bemenet megegyezett-e trueMatch-el.
     */
    public static boolean getUserInput(String message, String trueMatch)
    {
        boolean ret = false;
        System.out.println(message);
        Scanner sc = new Scanner(System.in);
        ret = sc.nextLine().equals(trueMatch);
        sc.close();
        return ret;
    }

    /**
     * 5.1.2.1.-es teszteset
     * Lebénult virológus megpróbál Aminosavat lopni egy másiktól,
     * de mivel béna, ezért nem tud.
     */
    private static void test1(){
        Virologist v1 = new Virologist();
        NoLoot nl = new NoLoot();
        Virologist v2 = new Virologist();
        v1.SetLootStr(nl);

        v1.LootAminoAcidFrom(v2);
    }

    /**
     * 5.1.2.2.-es teszteset
     * Egy virológus megpróbál egy másikat kirabolni (felszerelést lopni tőle), de mivel az nem bénult,
     * ezért nem lesz sikeres.
     * Ha a virológusnál 3 felszerelés van, akkor se lesz sikeres, de akkor az 5.1.2.3.-as teszteset
     * fog végrehajtódni.
     */
    private static void test2(){
        Virologist v1 = new Virologist();
        Virologist v2 = new Virologist();

        v1.LootEquipmentFrom(v2);
    }

    /**
     * 5.1.2.4.-es teszteset
     * Egy virológus nukleotidot lop egy másik, lebénult virológustól. Aminosav lopás is ugyanígy nézne ki.
     * Akkor fog a várt eredménnyel lefutni, ha a virológusnak van még hátra akciója.
     */
    private static void test4(){
        Virologist v1 = new Virologist();
        Virologist v2 = new Virologist();
        Looted ltd = new Looted();
        v2.SetLootedStr(ltd);
        v2.AddNucleotide(20);

        v1.LootNucleotideFrom(v2);
    }

    /**
     * 5.1.2.5.-ös teszteset
     * Egy virológus egy másik, bénult virológustól szeretne felszerelést lopni, de annak nincs.
     * Akkor fog a várt eredménnyel lefutni, ha a virológusnak van még hátra akciója és van helye
     * plusz felszerelésre.
     */
    private static void test5(){
        Virologist v1 = new Virologist();
        Virologist v2 = new Virologist();
        Looted lt = new Looted();
        v2.SetLootedStr(lt);

        v1.LootEquipmentFrom(v2);
    }

    /**
     * 5.1.2.6.-os teszteset
     * A virológus sikeresen lop egy köpenyt egy másik, bénult virológustól.
     * Akkor fog a várt eredménnyel lefutni, ha a virológusnak van még hátra akciója és van helye
     * plusz felszerelésre.
     */
    private static void test6(){
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
    private static void test7(){
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
    private static void test8(){
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
    private static void test9(){
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
    private static void test10(){
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
    private static void test11(){
        Virologist v = new Virologist();
        Shelter field = new Shelter();
        field.AddVirologist(v);
        Bag e = new Bag();
        field.Drop(e);

        v.Equip();
    }

    /**
     * 5.1.2.12-es teszteset
     * Azt a folyamatot szimulálja, mikor egy virológus megpróbál egy felszerelést felvenni egy óvóhelyről,
     * de az óvóhelyen nincsenek felszerelések.
     * Egyáltalán csak akkor próbálja meg felvenni, ha van akciója.
     */
    private static void test12(){
        Virologist v = new Virologist();
        Shelter field = new Shelter();
        field.AddVirologist(v);

        v.Equip();
    }

    /**
     * 5.1.2.13-as teszteset
     * Azt a folyamatot szimulálja, mikor egy virológus megakadályozódik abban,
     * hogy felvegyen egy felszerelést az adott mezőről.
     * Egyáltalán csak akkor próbálja meg, ha van akciója.
     */
    private static void test13(){
        Virologist v = new Virologist();
        NoEquip s = new NoEquip();
        v.SetEquipStr(s);

        v.Equip();
    }

    /**
     * 5.1.2.14-es teszteset
     * A virológus megpróbál megkenni egy másik virológust, de a virológus nem képes rá.
     * Csak akkor próbálkozik, ha van még hátra akciója.
     */
    private static void test14(){
        Virologist v = new Virologist();
        NoInject injectStr = new NoInject();
        BlockCode bCode = new BlockCode();
        Virologist target = new Virologist();
        v.AddgeneticCode(bCode);
        v.SetInjectStr(injectStr);

        v.Inject(target, bCode);
    }

    private static void test15(){}

    private static void test16(){}

    private static void test17(){}

    private static void test18(){}

    private static void test19(){}

    private static void test20(){}

    private static void test21(){}

    private static void test22(){}

    private static void test23(){}

    private static void test24(){}

    private static void test25(){}

    private static void test26(){}

    private static void test27(){}

    private static void test28(){}

    private static void test29(){}

    private static void test30(){}

    private static void test31(){}

    private static void test32(){}

    private static void test33(){}

    public static void menu(){
        System.out.println("1) 5.1.2.1 Stunned Virologist tries to loot AminoAcid\n" +
                "2) 5.1.2.2 Virologist tries to loot other Virologist who is not stunned\n" +
                "   5.1.2.3 Virologist tries to loot with 3 equipment stored already\n" +
                "4) 5.1.2.4 Virologist loots Nucleotide from other Virologist\n" +
                "5) 5.1.2.5 Virologist tries to loot equipment from other Virologist who has none\n" +
                "6) 5.1.2.6 Virologist tries to loot equipment from other Virologist who has a cloak\n" +
                "7) 5.1.2.7 Virologist moves to field\n" +

                "8) 5.1.2.1 Stunned Virologist tries to loot AminoAcid\n" +
                "9) 5.1.2.1 Stunned Virologist tries to loot AminoAcid\n" +
                "10) 5.1.2.1 Stunned Virologist tries to loot AminoAcid\n" +
                "11) 5.1.2.1 Stunned Virologist tries to loot AminoAcid\n" +
                "12) 5.1.2.1 Stunned Virologist tries to loot AminoAcid\n" +
                "13) 5.1.2.1 Stunned Virologist tries to loot AminoAcid\n" +
                "14) 5.1.2.1 Stunned Virologist tries to loot AminoAcid\n" +

                "a) 5.1.2.1 Stunned Virologist tries to loot AminoAcid\n" +
                "a) 5.1.2.1 Stunned Virologist tries to loot AminoAcid\n" +
                "a) 5.1.2.1 Stunned Virologist tries to loot AminoAcid\n" +
                "a) 5.1.2.1 Stunned Virologist tries to loot AminoAcid\n" +
                "a) 5.1.2.1 Stunned Virologist tries to loot AminoAcid\n" +
                "a) 5.1.2.1 Stunned Virologist tries to loot AminoAcid\n" +
                "a) 5.1.2.1 Stunned Virologist tries to loot AminoAcid\n" +
                "a) 5.1.2.1 Stunned Virologist tries to loot AminoAcid\n" +
                "a) 5.1.2.1 Stunned Virologist tries to loot AminoAcid\n" +
                "a) 5.1.2.1 Stunned Virologist tries to loot AminoAcid\n" +
                "a) 5.1.2.1 Stunned Virologist tries to loot AminoAcid\n" +
                "a) 5.1.2.1 Stunned Virologist tries to loot AminoAcid\n" +
                "a) 5.1.2.1 Stunned Virologist tries to loot AminoAcid\n" +
                "a) 5.1.2.1 Stunned Virologist tries to loot AminoAcid\n" +
                "a) 5.1.2.1 Stunned Virologist tries to loot AminoAcid\n" +
                "a) 5.1.2.1 Stunned Virologist tries to loot AminoAcid\n" +
                "a) 5.1.2.1 Stunned Virologist tries to loot AminoAcid\n" +
                "a) 5.1.2.1 Stunned Virologist tries to loot AminoAcid\n" +
                "a) 5.1.2.1 Stunned Virologist tries to loot AminoAcid\n" +
                "a) 5.1.2.1 Stunned Virologist tries to loot AminoAcid\n" +
                "a) 5.1.2.1 Stunned Virologist tries to loot AminoAcid\n" +
                "a) 5.1.2.1 Stunned Virologist tries to loot AminoAcid\n" +
                "a) 5.1.2.1 Stunned Virologist tries to loot AminoAcid\n" +
                "" +
                "99) EXIT");
    }

    public static void main(String[] args){
        /*HashMap<String, Method> tests = new HashMap<>();

        for (Method method : Tester.class.getMethods()){
            if (method.getName().matches("test(\\d*)")){
                tests.put(method.getName(), method);
            }
        }

        String input;
        while (!(input = sc.nextLine()).equals("exit")){
            Method m = tests.get(input);
            try {
                m.invoke(null); //kezelődik a null dereferálás, invoke exception-ök is.
            } catch (Exception e){
                System.out.println("Nem megfelelő input!");
            }
        }*/

        Scanner sc = new Scanner(System.in);
        boolean run = true;

        while(run){
            int test = Integer.parseInt(sc.nextLine());
            switch(test){
                case 1:
                    test1();
                    break;
                case 2:
                    test2();
                    break;
                case 4:
                    test4();
                    break;
                case 5:
                    test5();
                    break;
                case 6:
                    test6();
                case 7:
                    test7();
                    break;

                    //TODO ...

                case 99:
                    run = false;
                    break;
            }
        }
    }
}
