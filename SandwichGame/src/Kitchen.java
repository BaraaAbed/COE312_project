import java.util.ArrayList;

public class Kitchen extends Location {
    //initializing variables
    private static Kitchen instance; //singleton
    public boolean blocked;

    //constructor
    private Kitchen(){
        description = "You're now in Heck's kitchen, the place where Gordon Ramsay puts young chefs into one heck of a ride. At the entrance, there is a poster for some kind of cooking competition.";
        items.add(new Oven());
        items.add(new Fryer());
        items.add(new Blender());
        items.add(Sauce.getInstance());
        blocked = false;
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
