/**
 * A pályaleírónyelvben laboratóriumot jelképezõ osztály
 */
public class Laboratory extends Field{
    /**
     * Azonos típusú mezõ számlálója az automatikus elnevezéshez
     */
    private static int counter = 1;
    /**
     * Konstruktor
     */
    public Laboratory(){
        setName("L"+ counter);
        counter++;

        type = "Laboratory";

        equipment = null;

        int choice = Main.rnd.nextInt(Main.codes.length);
        param = Main.codes[choice];
    }
}
