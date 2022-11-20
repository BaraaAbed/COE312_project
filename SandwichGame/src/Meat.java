public class Meat extends Ingredient {
    private static Meat instance;

    private Meat() {

    }

    //gets instance (for singleton)
    public static synchronized Meat getInstance(){
        if(instance == null) instance = new Meat();
        return instance;
    }

    public void use() {
        System.out.println("Eating this meat alone would ruin the sandwich!\n"
                        + "Meat stayed in the inventory.");
    }
}
