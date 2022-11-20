public class Mushroom extends Ingredient {
    private static Mushroom instance;

    private Mushroom() {

    }

    //gets instance (for singleton)
    public static synchronized Mushroom getInstance(){
        if(instance == null) instance = new Mushroom();
        return instance;
    }

    public void use() {
        System.out.println("Eating this mushroom alone would ruin the sandwich!\n"
                        + "Mushroom stayed in the inventory.");
    }
}
