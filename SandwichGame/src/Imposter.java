public class Imposter extends Enemy {
    private static Imposter instance;

    private Imposter() {
        health = 100;
        dmg = 10;
        description = "There is an Imposter among us...";
    }

    //gets instance (for singleton)
    public static synchronized Imposter getInstance(){
        if(instance == null) instance = new Imposter();
        return instance;
    }
}
