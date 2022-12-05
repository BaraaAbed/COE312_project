import java.util.ArrayList;

public class House extends Location {
    //initializing variables
    private static House instance; //singleton

    //constructor
    private House(){
        description = "This is your house. This is where the whole journey started, and where you plan to end it.";
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
        if(!nearby.isEmpty())nearby.clear();
        nearby.add(Road.getInstance());
    }
    
    @Override
    public String toString(){
        return "House";
    }

}
