import java.util.ArrayList;

public class Farm extends Location {
    //initializing variables
    private static Farm instance; //singleton

    //constructor
    private Farm() {
        description = "The farm is filled with a variety of crops carrying delicious-looking fruits and vegetables, reminding you of your severe hunger. There seems to be special fencing around the lettuce crops, with a worn-down scarecrow in the middle.";
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
