import java.util.ArrayList;

public class House extends Location {
    //initializing variables
    private static House instance; //singleton

    //constructor
    private House(){
        description = "";
        items.add(new Fridge());
        items.add(Bread.getInstance());
    }

    //gets instance (for singleton)
    public static synchronized House getInstance(){
        if(instance == null) instance = new House();
        return instance;
    }

    //nearby function
    @Override
    public void getNearby(ArrayList<Location> nearby) {
        nearby.clear();
        nearby.add(Road.getInstance());
    }
    
}
