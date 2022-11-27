public class Bob extends NPC {
    private static Bob instance;

    private Bob() {
        description = "There is a cashier in the supermarket... you can ask him about the ingredients you need";
    }

    //gets instance (for singleton)
    public static synchronized Bob getInstance(){
        if(instance == null) instance = new Bob();
        return instance;
    }
}
