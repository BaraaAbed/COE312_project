public class RatKing extends Enemy {
    private static RatKing instance;

    private RatKing() {
        baseHealth = 200;
        health = baseHealth;
        dmg = 20;
        description = "You have awoken the Rat King from his slumber on his throne! You will not get away with this!";
    }

    //gets instance (for singleton)
    public static synchronized RatKing getInstance(){
        if(instance == null) instance = new RatKing();
        return instance;
    }

    @Override
    public String toString() {
        return "Rat King";
    }

    @Override
    public void makeTakable() {
        Cheese.getInstance().takable = true;
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
