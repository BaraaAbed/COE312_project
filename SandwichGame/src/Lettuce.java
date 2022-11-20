public class Lettuce extends Ingredient {
    private static Lettuce instance;

    private Lettuce() {

    }

    //gets instance (for singleton)
    public static synchronized Lettuce getInstance(){
        if(instance == null) instance = new Lettuce();
        return instance;
    }

    public void use() {
        System.out.println("Eating this lettuce alone would ruin the sandwich!\n"
                        + "Lettuce stayed in the inventory.");
    }
}
