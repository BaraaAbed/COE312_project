public class Bread extends Ingredient {
    private static Bread instance;

    private Bread() {

    }

    //gets instance (for singleton)
    public static synchronized Bread getInstance(){
        if(instance == null) instance = new Bread();
        return instance;
    }

    public void use() {
        System.out.println("Eating this bread alone would ruin the sandwich!\n"
                        + "Bread stayed in the inventory.");
    }
}
