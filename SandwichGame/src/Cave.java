import java.util.ArrayList;

public class Cave extends Location {
    //initializing variables
    private static Cave instance; //singleton
    private boolean blocked; 

    //constructor
    private Cave(){
        description = "";
        items.add(Meat.getInstance());
        enemy = Phoenix.getInstance();
        blocked = true;//fire not extinquished
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
        nearby.clear();
        nearby.add(Wilderness.getInstance());
    }
}
