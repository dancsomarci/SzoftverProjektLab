import java.io.IOException;

/**
 * A pályaleíró nyelvben egy mezõt jelképezõ osztály és paraméterei
 */
public class Field {
    /**
     * Azonos típusú mezõ számlálója az automatikus elnevezéshez
     */
    private static int counter = 1;
    /**
     * Mezõ típusa
     */
    protected String type;
    /**
     * Mezõ neve
     */
    private String name;
    /**
     * Mezõn található felszerelés
     */
    protected String equipment;
    /**
     * Mezõ paramétere (pl. genetikai kód, vagy felszerelés)
     */
    protected String param;

    /**
     * Mezõ konstruktora
     */
    public Field(){
        setName("F"+ counter);
        counter++;

        type = "Field";


        int choice = Main.rnd.nextInt(Main.equipments.length+1);
        if(choice == 0)
            equipment = null;
        else
            equipment = Main.equipments[choice-1];

        param = null;
    }

    /**
     * Mezõt kiíró függvény
     * @throws IOException ha nem sikerült a file-ba írás
     */
    public void print() throws IOException {

        Main.fw.append("Field\nType ").append(type).append("\n");
        if(param != null)
            Main.fw.append("Param ").append(param).append("\n");
        Main.fw.append("Name ").append(getName()).append("\n");
        if(equipment != null)
            Main.fw.append("Equipment ").append(equipment).append("\n");
        Main.fw.append("end\n");
    }

    /**
     * @return mezõ neve
     */
    public String getName() {
        return name;
    }

    /**
     * Mezõ nevének beállítása
     * @param name a beállítandó név
     */
    public void setName(String name) {
        this.name = name;
    }
}
