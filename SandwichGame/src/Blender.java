public class Blender extends HeckItems {
    Blender(){
        //description = "There is a perfectly working blender on the kitchen counter."
                        //+" Would be a shame if someone tampered with it...";
    }

    public void use() {
        System.out.println("Blender sabotaged!");
    }

    @Override
	public void fristDamage() {
        System.out.println("To loosen the screw, shake it quickly!");
		UIClient.failedSabo = !TCP_Client.avgAccAboveThreshold('X', 5, 3.5);
	}

	@Override
	public void secondDamage() {
        System.out.println("To loosen the screw, rotate it to the left!");
		UIClient.failedSabo = !TCP_Client.peakGyroAboveThreshold('Z', 5, 15);
	}

	@Override
	public void thirdDamage() {
        System.out.println("To loosen the screw, rotate it to the right!");
		UIClient.failedSabo = !TCP_Client.minGyroBelowThreshold('Z', 5, -15);	
	}
}
