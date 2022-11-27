public class Blender extends HeckItems {
    Blender(){
        description = "There is a perfectly working blender on the kitchen counter."
                        +" Would be a shame if someone tampered with it...";
    }

    public void use() {
        System.out.println("Blender sabotaged!");
    }
}
