public class Lettuce extends Ingredient {
    private static Lettuce instance;

    private Lettuce() {
        description = "There seems to be something glowing from beneath the scarecrow...";
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

    @Override
    public String toString() {
        return "Lettuce";
    }
}
