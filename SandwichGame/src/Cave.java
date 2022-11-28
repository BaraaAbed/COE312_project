import java.util.ArrayList;

public class Cave extends Location {
    //initializing variables
    private static Cave instance; //singleton
    private boolean blocked; 

    //constructor
    private Cave() {
        description = "The cave is big and dark...but there appears to be something big and glowing deep inside the cave";
        items.add(Meat.getInstance());
        enemy = Phoenix.getInstance();
        blocked = true; //fire not extinguished
    }

    //gets instance (for singleton)
    public static synchronized Cave getInstance(){
        if(instance == null) instance = new Cave();
        return instance;
    }

    //extinguish fire
    public void extinguish(){
        blocked = false;
    }

    //nearby function
    @Override
    public void getNearby(ArrayList<Location> nearby) {
        if(!nearby.isEmpty())nearby.clear();
        nearby.add(Wilderness.getInstance());
    }

    @Override
    public String toString(){
        return "Cave";
    }
}
