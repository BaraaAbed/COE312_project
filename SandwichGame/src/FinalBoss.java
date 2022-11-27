public class FinalBoss extends Enemy {
    private static FinalBoss instance;

    private FinalBoss() {
        health = 100;
        dmg = 10;
        description = "The legendary sandwich turned into a sentient monster sandwich! You must defeat it to defend your home and finally have the lunch you have longed for since the beginning!";
    }

    //gets instance (for singleton)
    public static synchronized FinalBoss getInstance(){
        if(instance == null) instance = new FinalBoss();
        return instance;
    }
}
