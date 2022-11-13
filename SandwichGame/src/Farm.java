import java.util.ArrayList;

public class Farm extends Location {
    //initializing variables
    private static Farm instance; //singleton

    //constructor
    private Farm(){
        description = "";
        items.add(new Torch());
        items.add(Lettuce.getInstance());
        enemy = Scarecrow.getInstance();
    }

    //gets instance (for singleton)
    public static synchronized Farm getInstance(){
        if(instance == null) instance = new Farm();
        return instance;
    }

    //nearby function
    @Override
    public void getNearby(ArrayList<Location> nearby) {
        nearby.clear();
        nearby.add(Wilderness.getInstance());
    }
}
