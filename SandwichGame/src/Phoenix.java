public class Phoenix extends Enemy {
    private static Phoenix instance;

    private Phoenix() {
        health = 100;
        dmg = 10;
    }

    //gets instance (for singleton)
    public static synchronized Phoenix getInstance(){
        if(instance == null) instance = new Phoenix();
        return instance;
    }
}
