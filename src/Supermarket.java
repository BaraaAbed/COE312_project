import java.util.ArrayList;

public class Supermarket extends Location {
    //initializing variables
    private static Supermarket instance; //singleton

    //constructor
    private Supermarket(){
        description = "This is the local supermarket. You look around to find it unusually empty, almost as if you are the only customer. The only other person you can see is the cashier at the counter.";
        npcs.add(Bob.getInstance());
    }

    //gets instance (for singleton)
    public static synchronized Supermarket getInstance(){
        if(instance == null) instance = new Supermarket();
        return instance;
    }

    //nearby function
    @Override
    public void getNearby(ArrayList<Location> nearby) {
        if(!nearby.isEmpty())nearby.clear();
        nearby.add(Road.getInstance());
    }

    @Override
    public String toString(){
        return "Supermarket";
    }
}
