import java.util.ArrayList;

public class Wilderness extends Location {
    //initializing variables
    private static Wilderness instance; //singleton

    //constructor
    private Wilderness(){
        description = "This is the wilderness. You can get to many locations from here.";
    }

    //gets instance (for singleton)
    public static synchronized Wilderness getInstance(){
        if(instance == null) instance = new Wilderness();
        return instance;
    }

    //nearby function
    @Override
    public void getNearby(ArrayList<Location> nearby) {
        if(!nearby.isEmpty())nearby.clear();
        nearby.add(Road.getInstance());
        nearby.add(Farm.getInstance());
        nearby.add(Warehouse.getInstance());
        nearby.add(Cave.getInstance());
        nearby.add(Forest.getInstance());
    }

    @Override
    public String toString(){
        return "Wilderness";
    }
}
