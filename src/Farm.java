import java.util.ArrayList;

public class Farm extends Location {
    //initializing variables
    private static Farm instance; //singleton

    //constructor
    private Farm() {
        description = "You are in the Abandoned Farm. The atmoshphere here is a bit too spooky for your liking, you should do what you have to do here and leave as soon as possible.";
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
        if(!nearby.isEmpty())nearby.clear();
        nearby.add(Wilderness.getInstance());
    }

    @Override
    public String toString(){
        return "Farm";
    }
}
