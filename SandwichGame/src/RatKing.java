public class RatKing extends Enemy {
    private static RatKing instance;

    private RatKing() {
        health = 100;
        dmg = 10;
        description = "You have awoken the Rat King from his slumber on his throne! You will not get away with this!";
    }

    //gets instance (for singleton)
    public static synchronized RatKing getInstance(){
        if(instance == null) instance = new RatKing();
        return instance;
    }
}
