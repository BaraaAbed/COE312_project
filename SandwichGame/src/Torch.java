public class Torch extends Item {
    public static boolean on;

    Torch(){
        on = false;
        description = "There is a torch half buried into the dirt... It might be useful for later, maybe you should pick it up.";
    }

    public void use() {
        if(!on) {
            System.out.println("Torch turned on!");
            on = true;
        }
        else {
            System.out.println("Torch turned off.");
            on = false;
        }
    }

    @Override
    public String toString() {
        return "Torch";
    }
}
