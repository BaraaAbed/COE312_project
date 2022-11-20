public class FinalBoss extends Enemy {
    private static FinalBoss instance;

    private FinalBoss() {
        health = 100;
        dmg = 10;
    }

    //gets instance (for singleton)
    public static synchronized FinalBoss getInstance(){
        if(instance == null) instance = new FinalBoss();
        return instance;
    }
}
