public class Trap extends Item {

    Trap() {
        description = "There may or may not be traps in this area...";
    }

    public void use() {
        System.out.println("Trap activated!");
    }
}
