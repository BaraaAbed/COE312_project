public class Sauce extends Ingredient {
    private static Sauce instance;

    private Sauce() {

    }

    //gets instance (for singleton)
    public static synchronized Sauce getInstance(){
        if(instance == null) instance = new Sauce();
        return instance;
    }

    public void use() {
        System.out.println("Eating this sauce alone would ruin the sandwich!\n"
                        + "Sauce stayed in the inventory.");
    }
}
