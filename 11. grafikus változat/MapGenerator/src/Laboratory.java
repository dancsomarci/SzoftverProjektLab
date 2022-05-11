import java.util.Random;

public class Laboratory extends Field{
    private static int counter = 1;

    public Laboratory(){
        name = "L"+ counter;
        counter++;

        type = "Laboratory";

        equipment = null;

        int choice = Main.rnd.nextInt(Main.codes.length);
        param = Main.codes[choice];
    }
}
