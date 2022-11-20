public class Cashier extends NPC {
    private static Cashier instance;

    private Cashier() {

    }

    //gets instance (for singleton)
    public static synchronized Cashier getInstance(){
        if(instance == null) instance = new Cashier();
        return instance;
    }
    
    public void talk() {
        System.out.println("Hello, how can i help you today?");
    }
}
