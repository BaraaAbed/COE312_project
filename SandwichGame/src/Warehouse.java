import java.util.ArrayList;

public class Warehouse extends Location {
    //initializing variables
    private static Warehouse instance; //singleton
    private boolean dark;

    //constructor
    private Warehouse(){
        description = "silence... The warehouse is dark and quiet, and you hear a suspicious sound...";
        items.add(Tomato.getInstance());
        enemy = Imposter.getInstance();
        dark = true;
    }

    //gets instance (for singleton)
    public static synchronized Warehouse getInstance(){
        if(instance == null) instance = new Warehouse();
        return instance;
    }

    //setter for dark
    public void setDark(boolean state){
        dark = state;
    }

    //nearby function
    @Override
    public void getNearby(ArrayList<Location> nearby) {
        if(!nearby.isEmpty())nearby.clear();
        nearby.add(Wilderness.getInstance());
    }

    @Override
    public String toString(){
        return "Warehouse";
    }
}
