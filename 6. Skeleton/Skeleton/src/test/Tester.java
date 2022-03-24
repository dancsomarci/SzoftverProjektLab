package test;

import model.Virologist;
import model.equipments.Cloak;
import model.map.Field;
import model.strategy.Looted;
import model.strategy.NoLoot;

import java.lang.reflect.Method;

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
    public static void ctrMethodStart(Method m){

        System.out.println(m.getName()+"()");
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

    private static void test8(){}

    private static void test9(){}

    private static void test10(){}

    private static void test11(){}

    private static void test12(){}

    private static void test13(){}

    private static void test14(){}

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

    public static void main(String[] args){

        //TODO menü

    }

}
