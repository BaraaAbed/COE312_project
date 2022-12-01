public class Imposter extends Enemy {
    private static Imposter instance;

    private Imposter() {
        baseHealth = 300;
        health = baseHealth;
        dmg = 24;
        description = "There is an Imposter among us...";
    }

    //gets instance (for singleton)
    public static synchronized Imposter getInstance(){
        if(instance == null) instance = new Imposter();
        return instance;
    }

    @Override
    public String toString() {
        return "Imposter";
    }

    @Override
    public void makeTakable() {
        Tomato.getInstance().takable = true;
    }

	@Override
	public double getAttackDur() {
		return 4;
	}

	@Override
	public double getDodgeDur() {
		return 1.8;
	}

}
