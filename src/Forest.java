import java.util.ArrayList;

public class Forest extends Location {
    //initializing variables
    private static Forest instance; //singleton
    public boolean pathCrossed;

    //constructor
    private Forest(){
        description = "The forest is dense and full of trees... Here, you find two paths heading somewhere, and a sign in between with the words \"PATH OF THE GNOME\" written on it.";
        items.add(new FireExtinguisher());
        items.add(Mushroom.getInstance());
        enemy = Gnome.getInstance();
        pathCrossed = false;
    }

    //gets instance (for singleton)
    public static synchronized Forest getInstance(){
        if(instance == null) instance = new Forest();
        return instance;
    }

    //nearby function
    @Override
    public void getNearby(ArrayList<Location> nearby) {
        if(!nearby.isEmpty())nearby.clear();
        nearby.add(Wilderness.getInstance());
    }
    
    @Override
    public String toString(){
        return "Forest";
    }
}
