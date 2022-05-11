import java.util.Random;

public class Shelter extends  Field {
    private static int counter = 1;

    public Shelter(){
        name = "S"+ counter;
        counter++;

        type = "Shelter";

        int choice = Main.rnd.nextInt(Main.equipments.length);
        param = Main.equipments[choice];

        equipment = null;
    }
}
