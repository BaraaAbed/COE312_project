public class Oven extends HeckItems {
    Oven(){
        description = "There is a perfectly working oven under the kitchen counter."
                        +" Would be a shame if someone tampered with it...";
    }

    public void use() {
        System.out.println("Oven sabotaged!");
    }
}
