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

        Random rnd = new Random();
        int choice = rnd.nextInt(Main.equipments.length+1);
        if(choice == 0)
            equipment = null;
        else
            equipment = Main.equipments[choice-1];

        param = null;
    }

    public void print(String path) throws IOException {
        FileWriter fw = new FileWriter(path);
        fw.append("Field\n" +
                "Type "+type+"\n");
        if(param != null)
            fw.append("Param "+param+"\n");
        fw.append("Name "+ name+"\n");
        if(equipment != null)
            fw.append("Equipment "+equipment+"\n");
        fw.append("end\n");
        fw.close();
    }
}
