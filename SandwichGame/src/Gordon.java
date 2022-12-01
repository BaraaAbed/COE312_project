public class Gordon extends Enemy {
    private static Gordon instance;

    private Gordon() {
        health = 100;
        dmg = 10;
        description = "Gordon Ramsay is here and he is agitated about the quality of food made by the chefs! Do not anger him any more or he will unleash his wrath on you!";
    }

    //gets instance (for singleton)
    public static synchronized Gordon getInstance(){
        if(instance == null) instance = new Gordon();
        return instance;
    }

    @Override
    public String toString() {
        return "Gordon";
    }

	@Override
	public double getAttackDur() {
		return 0;
	}

	@Override
	public double getDodgeDur() {
		return 0;
	}
}
