import java.util.ArrayList;

public class Road extends Location {
    //initializing variables
    private static Road instance; //singleton

    //constructor
    private Road(){
        description = "There is a long road ahead. It seems conveniently connected to many of the city's landmarks.";
    }

    //gets instance (for singleton)
    public static synchronized Road getInstance(){
        if(instance == null) instance = new Road();
        return instance;
    }

    //nearby function
    @Override
    public void getNearby(ArrayList<Location> nearby) {
        if(!nearby.isEmpty())nearby.clear();
        nearby.add(Wilderness.getInstance());
        nearby.add(Kitchen.getInstance());
        nearby.add(House.getInstance());
        nearby.add(Sewers.getInstance());
        nearby.add(Supermarket.getInstance());
    }

    @Override
    public String toString(){
        return "Road";
    }
}
