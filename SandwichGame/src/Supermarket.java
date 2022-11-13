import java.util.ArrayList;

public class Supermarket extends Location {
    //initializing variables
    private static Supermarket instance; //singleton

    //constructor
    private Supermarket(){
        description = "";
        npcs.add(Cashier.getInstance());
    }

    //gets instance (for singleton)
    public static synchronized Supermarket getInstance(){
        if(instance == null) instance = new Supermarket();
        return instance;
    }

    //nearby function
    @Override
    public void getNearby(ArrayList<Location> nearby) {
        nearby.clear();
        nearby.add(Road.getInstance());
    }
}
