public class FireExtinguisher extends Item {

    public FireExtinguisher(){
        description = "You find a... fire extinguisher? Well, it might be useful for later. ";
    }

    public void use() {
        if(Player.currentLocation == Cave.getInstance()) {
            System.out.println("Fire extinguisher put out the fire! Path is clear.");
            Cave.getInstance().blocked = false;
        }
        else System.out.println("No fire to put out");
    }

    @Override
    public String toString() {
        return "Extinguisher";
    }
}
