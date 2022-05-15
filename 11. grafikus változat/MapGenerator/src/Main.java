
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * A program vezérlõ osztálya
 */
public class Main {
    /**
     * A pályaleírónyelv által elfogadott felszerelések nevei
     */
    public static String[] equipments = {"Axe", "Bag", "Cloak", "Glove"};
    /**
     * A pályaleírónyelv által elfogadott genetikai kódok nevei
     */
    public static String[] codes = {"BlockCode", "StunCode", "ChoreaCode", "ForgetCode"};
    /**
     * A létrehozandó pálya méretének arányossági tényezõje
     */
    private static int size = 5;
    /**
     * A létrehozandó virológusok nevei - opcionális
     */
    private static ArrayList<String> names;
    /**
     * A létrehozott mezõk
     */
    public static ArrayList<Field> fields;
    /**
     * A pályát file-ba kiíró eszköz
     */
    public static  FileWriter fw;
    /**
     * Központi random generátor
     */
    public static Random rnd;

    /**
     * A program belépési pontja
     * @param args [játékosok száma: egész szám, kötelezõ], [a létrehozandó file elérési útja: ""-kel övezett szöveg, opcionális],
     *             [a pálya mérete: egész szám, ennek a játékosok számával vett szorzatával fog megegyezni a mezõk száma, opcionális
     *              - de feltétele a korábbi összes opcionális paraméter megadása kötelezõ jelleggel -], [a létrehozandó virológusok nevei:
     *              egymás utan ""-kel övezve tetszõleges sok számú virológusnév megadható, opcionális
     *              - de feltétele a korábbi összes opcionális paraméter megadása kötelezõ jellgel -]
     * @throws IOException ha nem sikerült a file-ba írás
     */
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

    /**
     * Mezõk generálása
     * @param playerCount játékosok száma
     * @throws IOException ha nem sikerült a file-ba írás
     */
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
                    fw.append(fields.get(i).getName()).append(" ").append(fields.get(j).getName()).append("\n");
                    break;
                }
                if(j == i-1){
                    fw.append(fields.get(i).getName()).append(" ").append(fields.get(j).getName()).append("\n");
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
                        fw.append(fields.get(i).getName()).append(" ").append(fields.get(j).getName()).append("\n");
                    }
                }
            }
        }

        fw.append("end\n");
    }

    /**
     * Virológusok generálása
     * @param playerCount játékosok száma
     * @throws IOException ha nem sikerült a file-ba írás
     */
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
