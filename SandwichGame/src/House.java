import java.util.ArrayList;

public class House extends Location {
    //initializing variables
    private static House instance; //singleton

    //constructor
    private House(){
        description = "This is your house, there is a fridge...if you are feeling hungry, then perhaps you should check what is inside.";
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
