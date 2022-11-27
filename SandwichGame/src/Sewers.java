import java.util.ArrayList;

public class Sewers extends Location {
    //initializing variables
    private static Sewers instance; //singleton

    //constructor
    private Sewers(){
        description = "";
        enemy = RatKing.getInstance();
    }

    //gets instance (for singleton)
    public static synchronized Sewers getInstance(){
        if(instance == null) instance = new Sewers();
        return instance;
    }

    //nearby function
    @Override
    public void getNearby(ArrayList<Location> nearby) {
        if(!nearby.isEmpty())nearby.clear();
        nearby.add(Road.getInstance());
        nearby.add(RatHouse.getInstance());
    }

    @Override
    public String toString(){
        return "Sewers";
    }
}
