public class Warehouse extends Field{
    private static int counter = 1;
    public Warehouse(){
        name = "W"+ counter;
        counter++;

        type = "Warehouse";

        equipment = null;

        param = null;
    }
}
