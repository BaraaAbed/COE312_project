public class Cheese extends Ingredient {
    private static Cheese instance;

    private Cheese() {
        description = "There seems to be something yellow glowing on the Rat King's throne...";
    }

    //gets instance (for singleton)
    public static synchronized Cheese getInstance(){
        if(instance == null) instance = new Cheese();
        return instance;
    }

    public void use() {
        System.out.println("Eating this cheese alone would ruin the sandwich!\n"
                        + "Cheese stayed in the inventory.");
    }
}
