import java.util.Random;

public class Shelter extends  Field {
    private static int counter = 1;

    public Shelter(){
        name = "S"+String.valueOf(counter);
        counter++;

        type = "Shelter";

        Random rnd = new Random();
        int choice = rnd.nextInt(Main.equipments.length);
        equipment = Main.equipments[choice-1];

        param = null;
    }
}
