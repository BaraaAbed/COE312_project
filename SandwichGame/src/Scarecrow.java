public class Scarecrow extends Enemy {
    private static Scarecrow instance;

    private Scarecrow() {
        baseHealth = 100;
        health = baseHealth;
        dmg = 15;
        description = "The scarecrow has been here all his life defending the crops from the pesky crows. There may also be something else he is trying to protect...";
    }

    //gets instance (for singleton)
    public static synchronized Scarecrow getInstance(){
        if(instance == null) instance = new Scarecrow();
        return instance;
    }

    @Override
    public String toString() {
        return "Scarecrow";
    }

    @Override
    public void makeTakable() {
        Lettuce.getInstance().takable = true;
    }

	@Override
	public double getAttackDur() {
		return 5;
	}

	@Override
	public double getDodgeDur() {
		return 2;
	}
}
