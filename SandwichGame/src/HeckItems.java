public class HeckItems extends Item {
    //constructor
    public HeckItems(){
        takable = false;
    }

    public void use() {
        System.out.println("Heck's kitchen item sabotaged!");
    }
}
