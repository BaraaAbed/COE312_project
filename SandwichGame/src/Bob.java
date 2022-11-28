public class Bob extends NPC {
    private static Bob instance;

    private Bob() {
        description = "You find Bob doing the usual at the counter... you can ask him about the ingredients you need";
    }

    //gets instance (for singleton)
    public static synchronized Bob getInstance(){
        if(instance == null) instance = new Bob();
        return instance;
    }

    @Override
    public String toString() {
        return "Bob";
    }
}
