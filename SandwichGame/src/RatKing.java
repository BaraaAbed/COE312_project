public class RatKing extends Enemy {
    private static RatKing instance;

    private RatKing() {
        health = 100;
        dmg = 10;
    }

    //gets instance (for singleton)
    public static synchronized RatKing getInstance(){
        if(instance == null) instance = new RatKing();
        return instance;
    }
}
