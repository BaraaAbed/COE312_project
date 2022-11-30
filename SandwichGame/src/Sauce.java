public class Sauce extends Ingredient {
    private static Sauce instance;

    private Sauce() {
        description = "You glimpse the legendary lamb sauce shinning behind Gordon Ramsay. Maybe you can steal it if you join the cooking competition...";
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

    @Override
    public String toString() {
        return "Lamb sauce";
    }
}
