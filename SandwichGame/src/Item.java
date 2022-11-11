
public abstract class Item {
    //initializing variables
    private String description;
    private double dmgMultiplier;

    //Function that allows the item to be used
    public abstract void use();

    //description getter
    public String getDescription(){
        return description;
    }

    //getter for dmgMultiplier
    public double getDmgMultiplier(){
        return dmgMultiplier;
    }
}
