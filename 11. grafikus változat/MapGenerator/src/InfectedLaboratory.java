/**
 * A pályaleírónyelvben fertõzött laboratóriumot jelképezõ osztály
 */
public class InfectedLaboratory extends Field{
    /**
     * Azonos típusú mezõ számlálója az automatikus elnevezéshez
     */
    private static int counter = 1;

    /**
     * Konstruktor
     */
    public InfectedLaboratory(){
        setName("IL"+ counter);
        counter++;

        type = "InfectedLaboratory";

        equipment = null;

        int choice = Main.rnd.nextInt(Main.codes.length);
        param = Main.codes[choice];
    }
}
