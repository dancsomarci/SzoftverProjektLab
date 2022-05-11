
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Main {

    public static String[] equipments = {"Axe", "Bag", "Cloak", "Glove"};
    public static String[] codes = {"BlockCode", "StunCode", "ChoreaCode", "ForgetCode"};
    private static int size = 5;
    private static ArrayList<String> names;
    public static ArrayList<Field> fields;

    public static  FileWriter fw;
    public static Random rnd;
    public static void main(String[] args) throws IOException {
        if(args != null) {

            names = new ArrayList<>();
            int playerCount = Integer.parseInt(args[0]);
            String path = "map.txt";

            if(args.length > 1){

                path = args[1];

                if(args.length > 2){

                    size = Integer.parseInt(args[2]);

                    if(args.length > 3){

                        names.addAll(Arrays.asList(args).subList(3, args.length));
                    }
                }
            }

            fw = new FileWriter(path);
            rnd = new Random();

            createFields(playerCount);
            createPlayers(playerCount);

            fw.close();
        }
    }

    private static void createFields(int playerCount) throws IOException {
        fields = new ArrayList<>();

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
            field.print();
        }

        fw.append("Neighbours\n");

        //build tree of fields
        for(int i = 0; i < fields.size();i++){
            for(int j = 0; j < i; j++){
                boolean b = rnd.nextBoolean();
                if(b){
                    fw.append(fields.get(i).name).append(" ").append(fields.get(j).name).append("\n");
                    break;
                }
                if(j == i-1){
                    fw.append(fields.get(i).name).append(" ").append(fields.get(j).name).append("\n");
                }
            }
        }

        //create web of fields
        //double rows no problem, the model input ignores it
        for(int i = 0; i < fields.size();i++){
            for(int j = 0; j < fields.size(); j++){
                if(i != j) {
                    int b = rnd.nextInt(100);
                    if (b%4 == 0) {
                        fw.append(fields.get(i).name).append(" ").append(fields.get(j).name).append("\n");
                    }
                }
            }
        }

        fw.append("end\n");
    }

    private static void createPlayers(int playerCount) throws IOException {
        for(int i = 0; i < playerCount; i++){
            Virologist v;
            if(names.size() > i)
                v = new Virologist(names.get(i));
            else
                v = new Virologist();

            v.print();
        }
    }
}
