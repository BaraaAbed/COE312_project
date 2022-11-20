public class Tomato extends Ingredient {
    private static Tomato instance;

    private Tomato() {

    }

    //gets instance (for singleton)
    public static synchronized Tomato getInstance(){
        if(instance == null) instance = new Tomato();
        return instance;
    }

    public void use() {
        System.out.println("Eating this tomato alone would ruin the sandwich!\n"
                        + "Tomato stayed in the inventory.");
    }
}
