import java.util.ArrayList;

public class Warehouse extends Location {
    //initializing variables
    private static Warehouse instance; //singleton

    //constructor
    private Warehouse(){
        description = "silence... The warehouse is dark and quiet, and you hear a suspicious sound...";
        items.add(Tomato.getInstance());
        enemy = Imposter.getInstance();
    }

    //gets instance (for singleton)
    public static synchronized Warehouse getInstance(){
        if(instance == null) instance = new Warehouse();
        return instance;
    }


    //nearby function
    @Override
    public void getNearby(ArrayList<Location> nearby) {
        if(!nearby.isEmpty())nearby.clear();
        nearby.add(Wilderness.getInstance());
    }

    @Override
    public String toString(){
        return "Warehouse";
    }
}
