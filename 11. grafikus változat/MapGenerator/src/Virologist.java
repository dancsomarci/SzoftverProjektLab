import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Virologist {
    private String name;
    private int actions;
    private String equipment;
    private int amino;
    private int nucleo;
    private String code;
    private String pos;

    private static int counter = 1;

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

        pos = Main.fields.get(Main.rnd.nextInt(Main.fields.size())).name;
    }

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

        pos = Main.fields.get(rnd.nextInt(Main.fields.size())).name;
    }

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
