import java.util.ArrayList;

public class Wilderness extends Location {
    //initializing variables
    private static Wilderness instance; //singleton

    //constructor
    private Wilderness(){
        description = "";
    }

    //gets instance (for singleton)
    public static synchronized Wilderness getInstance(){
        if(instance == null) instance = new Wilderness();
        return instance;
    }

    //nearby function
    @Override
    public void getNearby(ArrayList<Location> nearby) {
        nearby.clear();
        nearby.add(Road.getInstance());
        nearby.add(Farm.getInstance());
        nearby.add(Warehouse.getInstance());
        nearby.add(Cave.getInstance());
        nearby.add(Forest.getInstance());
    }
}
