public class Gordon extends Enemy {
    private static Gordon instance;

    private Gordon() {
        health = 100;
        dmg = 10;
    }

    //gets instance (for singleton)
    public static synchronized Gordon getInstance(){
        if(instance == null) instance = new Gordon();
        return instance;
    }
}
