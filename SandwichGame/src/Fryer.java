public class Fryer extends HeckItems {
    Fryer(){
        //description = "There is a perfectly working fryer next to the kitchen counter."
                        //+" Would be a shame if someone tampered with it...";
    }

    public void use() {
        System.out.println("Fryer sabotaged!");
    }

	@Override
	public void firstDamage() {
        System.out.println("To untighten the screw, rotate it to the right!");
		UIClient.failedSabo = !TCP_Client.getInstance().minGyroBelowThreshold('Z', 5, -15);
	}

	@Override
	public void secondDamage() {
		System.out.println("To remove the screw, rotate it to the left!");
		UIClient.failedSabo = !TCP_Client.getInstance().peakGyroAboveThreshold('Z', 5, 15);	
	}

	@Override
	public void thirdDamage() {
		System.out.println("To bash the fryer, shake it quickly!");
		UIClient.failedSabo = !TCP_Client.getInstance().avgAccAboveThreshold('X', 5, 3.5);
	}

}
