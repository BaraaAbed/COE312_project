public class Torch extends Item {
    private boolean on;

    Torch(){
        on = false;
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
}
