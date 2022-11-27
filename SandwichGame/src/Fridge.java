public class Fridge extends Item {
    Fridge() {
        description = "There is a fridge in the kitchen...it might be worth checking what is inside";
    }

    public void use() {
        System.out.println("Fridge opened");
    }
}
