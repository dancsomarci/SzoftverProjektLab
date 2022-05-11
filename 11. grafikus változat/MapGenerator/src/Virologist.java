import java.io.IOException;
import java.util.Random;
/**
 * A pályaleírónyelvben a virológust jelképezõ osztály
 */
public class Virologist {
    /**
     * Virológus neve
     */
    private final String name;
    /**
     * Virológus hátralévõ akciói
     */
    private final int actions;
    /**
     * Virológus felszerelése
     */
    private final String equipment;
    /**
     * Virológus aminosav mennyisége
     */
    private final int amino;
    /**
     * Virológus nukleotid mennyisége
     */
    private final int nucleo;
    /**
     * Virológus genetikai kódja
     */
    private final String code;
    /**
     * Virológus pozíciója
     */
    private final String pos;
    /**
     * Számláló az automatikus elnevezéshez
     */
    private static int counter = 1;

    /**
     * Default konstruktor automata névadással
     */
    public Virologist(){


        name = "V"+counter;
        counter++;

        actions = Main.rnd.nextInt(3)+1;

        int choice = Main.rnd.nextInt(Main.equipments.length+1);
        if(choice == 0)
            equipment = null;
        else
            equipment = Main.equipments[choice-1];

        amino = Main.rnd.nextInt(20);
        nucleo = Main.rnd.nextInt(20);

        choice = Main.rnd.nextInt(Main.codes.length+1);
        if(choice == 0)
            code = null;
        else
            code = Main.codes[choice-1];

        pos = Main.fields.get(Main.rnd.nextInt(Main.fields.size())).getName();
    }

    /**
     * Konstruktor speciális névadással
     * @param name választott virológusnév
     */
    public Virologist(String name){
        Random rnd = new Random();

        this.name = name;

        actions = rnd.nextInt(3)+1;

        int choice = rnd.nextInt(Main.equipments.length+1);
        if(choice == 0)
            equipment = null;
        else
            equipment = Main.equipments[choice-1];

        amino = rnd.nextInt(20);
        nucleo = rnd.nextInt(20);

        choice = rnd.nextInt(Main.codes.length+1);
        if(choice == 0)
            code = null;
        else
            code = Main.codes[choice-1];

        pos = Main.fields.get(rnd.nextInt(Main.fields.size())).getName();
    }

    /**
     * Virológust kiíró függvény
     * @throws IOException ha nem sikerült a file-ba írás
     */
    public void print() throws IOException {

        Main.fw.append("Virologist\nName ").append(name).append("\nActionCount ").append(String.valueOf(actions)).append("\n");
        if(equipment != null)
            Main.fw.append("Equipment ").append(equipment).append("\n");
        Main.fw.append("Amino ").append(String.valueOf(amino)).append("\nNucleo ").append(String.valueOf(nucleo)).append("\n");
        if(code != null)
            Main.fw.append("GeneticCode ").append(code).append("\n");
        Main.fw.append("StartingPos ").append(pos).append("\nend\n");
    }

}
