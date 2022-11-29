public class Gnome extends Enemy {
    private static Gnome instance;

    private Gnome() {
        health = 100;
        dmg = 10;
        description = "There is a Gnome sneaking between these trees!";
    }

    //gets instance (for singleton)
    public static synchronized Gnome getInstance(){
        if(instance == null) instance = new Gnome();
        return instance;
    }

    @Override
    public String toString() {
        return "Gnome";
    }
}
