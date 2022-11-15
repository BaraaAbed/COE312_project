import java.util.ArrayList;

public class Forest extends Location {
    //initializing variables
    private static Forest instance; //singleton

    //constructor
    private Forest(){
        description = "";
        items.add(new FireExtinguisher());
        items.add(Mushroom.getInstance());
        enemy = Gnome.getInstance();
    }

    //gets instance (for singleton)
    public static synchronized Forest getInstance(){
        if(instance == null) instance = new Forest();
        return instance;
    }

    //nearby function
    @Override
    public void getNearby(ArrayList<Location> nearby) {
        nearby.clear();
        nearby.add(Wilderness.getInstance());
    }
    
}