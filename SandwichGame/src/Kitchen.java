import java.util.ArrayList;

public class Kitchen extends Location {
    //initializing variables
    private static Kitchen instance; //singleton

    //constructor
    private Kitchen(){
        description = "Your kitchen is where you make yourself some food to eat. There is a fridge.";
        items.add(new Oven());
        items.add(new Fryer());
        items.add(new Blender());
        items.add(Sauce.getInstance());
        enemy = Gordon.getInstance();
    }

    //gets instance (for singleton)
    public static synchronized Kitchen getInstance(){
        if(instance == null) instance = new Kitchen();
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
        return "Kitchen";
    }
}
