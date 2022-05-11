import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Field {
    private static int counter = 1;
    protected String type;
    public String name;
    protected String equipment;
    protected String param;
    public Field(){
        name = "F"+String.valueOf(counter);
        counter++;

        type = "Field";


        int choice = Main.rnd.nextInt(Main.equipments.length+1);
        if(choice == 0)
            equipment = null;
        else
            equipment = Main.equipments[choice-1];

        param = null;
    }

    public void print() throws IOException {

        Main.fw.append("Field\nType ").append(type).append("\n");
        if(param != null)
            Main.fw.append("Param ").append(param).append("\n");
        Main.fw.append("Name ").append(name).append("\n");
        if(equipment != null)
            Main.fw.append("Equipment ").append(equipment).append("\n");
        Main.fw.append("end\n");
    }
}
