public class Mushroom extends Ingredient {
    private static Mushroom instance;

    private Mushroom() {
        description = "There seems to be a glowing mushroom at the end of the paths.";
        takable = false;
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

    @Override
    public String toString() {
        return "Mushroom";
    }
}
