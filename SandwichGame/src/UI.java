import java.util.ArrayList;
import java.util.Scanner;

public class UI extends ConcreteSubject implements Runnable {
    //Variables
    public Thread t;
    public Scanner scan;
    private Message msg;
    private static UI instance;
    private Player player;
    private boolean cheatMode;

    //Constructor
    private UI(){
        t = new Thread(this);
        player = Player.getInstance();
        cheatMode = false;
        scan = new Scanner(System.in);
        msg = new Message(this, "Command", null);
        t.start();
    }

    //gets instance (for singleton)
    public static synchronized UI getInstance(){
        if(instance == null) instance = new UI();
        return instance;
    }

    @Override
    public void run() {
        while(scan.hasNextLine()){
            if (player.getLivingState() instanceof DeadState) {
                System.out.println("You are dead! Stop nagging and start begging!");
            }
            else {
                msg.payload = scan.nextLine();
                if (!cheatMode && msg.payload.toString().equalsIgnoreCase("I want to cheat so let me be! 88651")) { //used for quick debugging of final boss
                    System.out.println("Cheat mode active.");
                    cheatMode = true;
                    player.getIngredients().add(Lettuce.getInstance());
                    player.getIngredients().add(Tomato.getInstance());
                    player.getIngredients().add(Sauce.getInstance());
                    player.getIngredients().add(Mushroom.getInstance());
                    player.getIngredients().add(Cheese.getInstance());
                    player.getIngredients().add(Meat.getInstance());
                    player.setWeapon(new HighDamageWeapon());
                } else publishMessage(msg);
            }
        }
    }

    
}
