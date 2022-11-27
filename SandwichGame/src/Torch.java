public class Torch extends Item {
    private boolean on;

    Torch(){
        on = false;
        description = "There is a torch on the ground at the entrance to the warehouse...it might be useful to see what's inside...";
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
