public class Fridge extends Item {
    Fridge() {
        takable = false;
        description = "There is a fridge in the kitchen. It doesn't look like it is working anymore though. Maybe you should remember to close the fridge after you next time.";
    }

    public void use() {
        System.out.println("Fridge opened");
    }
}
