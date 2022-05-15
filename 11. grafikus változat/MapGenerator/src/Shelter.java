/**
 * A pályaleírónyelvben óvóhelyet jelképezõ osztály
 */
public class Shelter extends  Field {
    /**
     * Azonos típusú mezõ számlálója az automatikus elnevezéshez
     */
    private static int counter = 1;
    /**
     * Konstruktor
     */
    public Shelter(){
        setName("S"+ counter);
        counter++;

        type = "Shelter";

        int choice = Main.rnd.nextInt(Main.equipments.length);
        param = Main.equipments[choice];

        equipment = null;
    }
}
