import java.util.ArrayList;

public class Cave extends Location {
    //initializing variables
    private static Cave instance; //singleton
    public boolean blocked; 

    //constructor
    private Cave() {
        description = "After you extinguished the fire from the cave, you can finally have a look around. The cave is still very bright and you can see flames deep into the cave.";
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
    public String getDescription() {
        if(blocked) return "The floor is filled with flames. If you don't get rid of it soon, you will burn to death.";
        else return description;
    }

    @Override
    public String toString(){
        return "Cave";
    }
}
