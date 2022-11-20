public class Scarecrow extends Enemy {
    private static Scarecrow instance;

    private Scarecrow() {
        health = 100;
        dmg = 10;
    }

    //gets instance (for singleton)
    public static synchronized Scarecrow getInstance(){
        if(instance == null) instance = new Scarecrow();
        return instance;
    }
}
