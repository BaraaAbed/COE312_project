public class FinalBoss extends Enemy {
    private static FinalBoss instance;

    private FinalBoss() {
        baseHealth = 1000;
        health = baseHealth;
        dmg = 50;
        description = "The legendary sandwich turned into a sentient monster sandwich! You must defeat it to defend your home and finally have the lunch you have longed for since the beginning!";
    }

    //gets instance (for singleton)
    public static synchronized FinalBoss getInstance(){
        if(instance == null) instance = new FinalBoss();
        return instance;
    }

	@Override
	public double getAttackDur() {
		return 2.5;
	}

	@Override
	public double getDodgeDur() {
		return 1.1;
	}

    @Override
    public String toString() {
        return "Sandwich Emperor";
    }
}
