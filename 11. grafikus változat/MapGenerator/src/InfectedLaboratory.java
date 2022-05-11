import java.util.Random;

public class InfectedLaboratory extends Field{
    private static int counter = 1;

    public InfectedLaboratory(){
        name = "IL"+ counter;
        counter++;

        type = "InfectedLaboratory";

        equipment = null;

        int choice = Main.rnd.nextInt(Main.codes.length);
        param = Main.codes[choice];
    }
}
