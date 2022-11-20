
public abstract class Item {
    //initializing variables
    private String description;

    //Function that allows the item to be used
    public abstract void use();

    //description getter
    public String getDescription(){
        return description;
    }
}
