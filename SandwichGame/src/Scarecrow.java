public class Scarecrow extends Enemy {
    private static Scarecrow instance;

    private Scarecrow() {
        health = 100;
        dmg = 10;
        description = "The scarecrow has been here all his life defending the crops from the pesky crows...there may also be something else he is trying to protect...";
    }

    //gets instance (for singleton)
    public static synchronized Scarecrow getInstance(){
        if(instance == null) instance = new Scarecrow();
        return instance;
    }
}
