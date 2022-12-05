public class Phoenix extends Enemy {
    private static Phoenix instance;

    private Phoenix() {
        baseHealth = 500;
        health = baseHealth;
        dmg = 30;
        description = "You have awoken the legendary phoenix! You will pay the price!";
    }

    //gets instance (for singleton)
    public static synchronized Phoenix getInstance(){
        if(instance == null) instance = new Phoenix();
        return instance;
    }

    @Override
    public String toString() {
        return "Phoenix";
    }

    @Override
    public void makeTakable() {
        Meat.getInstance().takable = true;;
    }

	@Override
	public double getAttackDur() {
		return 3;
	}

	@Override
	public double getDodgeDur() {
		return 1.4;
	}
}
