public class FireExtinguisher extends Item {

    public FireExtinguisher(){
        description = "You find a... fire extinguisher? Well, it might be useful for later. ";
    }

    public void use() {
        System.out.println("Fire extinguisher put out the fire! Path is clear.");
    }

    @Override
    public String toString() {
        return "extinguisher";
    }
}
