
public abstract class Item {
    //initializing variables
    protected String description;
    public boolean takable;

    //constructor
    public Item(){
        takable = true;
    }

    //Function that allows the item to be used
    public abstract void use();

    //description getter
    public String getDescription(){
        return description;
    }
}
