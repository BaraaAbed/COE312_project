public class Fryer extends HeckItems {
    Fryer(){
        description = "There is a perfectly working fryer next to the kitchen counter."
                        +" Would be a shame if someone tampered with it...";
    }

    public void use() {
        System.out.println("Fryer sabotaged!");
    }
}
