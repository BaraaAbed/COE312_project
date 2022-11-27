import java.util.ArrayList;

public class RatHouse extends Location {
    //initializing variables
    private static RatHouse instance; //singleton

    //constructor
    private RatHouse(){
        description = "";
        items.add(Cheese.getInstance());
        enemy = RatKing.getInstance();
    }

    //gets instance (for singleton)
    public static synchronized RatHouse getInstance(){
        if(instance == null) instance = new RatHouse();
        return instance;
    }

    //nearby function
    @Override
    public void getNearby(ArrayList<Location> nearby) {
        if(!nearby.isEmpty())nearby.clear();
        nearby.add(Sewers.getInstance());
    }

    @Override
    public String toString(){
        return "Rat Castle";
    }
}
