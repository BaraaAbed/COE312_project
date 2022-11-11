import java.util.ArrayList;

public abstract class Location {
    //initializing variables
    private String description;
    public ArrayList<Item> items;//Stores the items that are founf in a location
    public Enemy enemy;//stores the enemy in this location
    public ArrayList<NPC> npcs;//stores the npcs that can be found in this location

    //Description getter
    public String getDescription(){
        return description;
    }

    //Function that updates the nearby arraylist in the player object
    public abstract ArrayList<Location> getNearby();
}
