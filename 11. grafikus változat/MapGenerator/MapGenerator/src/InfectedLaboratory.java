import java.util.Random;

public class InfectedLaboratory extends Field{
    private static int counter = 1;

    public InfectedLaboratory(){
        name = "IL"+String.valueOf(counter);
        counter++;

        type = "InfectedLaboratory";

        equipment = null;

        Random rnd = new Random();
        int choice = rnd.nextInt(Main.codes.length+1);
        if(choice == 0)
            param = null;
        else
            param = Main.codes[choice-1];
    }
}
