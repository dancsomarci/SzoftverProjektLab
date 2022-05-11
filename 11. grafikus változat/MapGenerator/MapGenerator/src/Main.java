import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Main {

    public static String[] equipments = {"Axe", "Bag", "Cloak", "Glove"};
    public static String[] codes = {"Block", "Stun", "Chorea", "Forget"};
    private static int size = 5;

    public static void main(String[] args) throws IOException {
        if(args != null) {
            int playerCount = Integer.parseInt(args[0]);
            String path = "map.txt";
            if(args.length > 1){
                path = args[1];
                if(args.length > 2){
                    size = Integer.parseInt(args[2]);
                }
            }

            createFields(playerCount, path);
            createPlayers(playerCount, path);

        }
    }

    private static void createFields(int playerCount, String path) throws IOException {
        ArrayList<Field> fields = new ArrayList<>();

        Random rnd = new Random();

        for(int i = 0; i< playerCount*size;i++){
            int j = rnd.nextInt(5);
            switch(j){
                case 0:
                    fields.add(new Field());
                    break;
                case 1:
                    fields.add(new Shelter());
                    break;
                case 2:
                    fields.add(new Warehouse());
                    break;
                case 3:
                    fields.add(new Laboratory());
                    break;
                case 4:
                    fields.add(new InfectedLaboratory());
                    break;
            }
        }

        for(Field field: fields){
            field.print(path);
        }

        

    }

    private static void createPlayers(int playerCount, String path){

    }
}
