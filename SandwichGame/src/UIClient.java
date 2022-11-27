import java.util.ArrayList;
import java.util.Scanner;

public class UIClient extends ConcreteObserver implements Runnable{
    //initializing variables
    public static boolean fighting;
    public static Enemy fightingEnemy;
    public Thread t;
    private String[] commInput;
    private boolean isUpdate;
    public static boolean getAcc;
    public static boolean getDB;
    public static boolean getHeading;
    public static boolean getOrientation;
    private Player player;
    private Scanner scan;
    

    //constructor
    public UIClient(ArrayList<ConcreteSubject> subjects){
        super(subjects);
        getAcc = false;
        scan = new Scanner(System.in);
        player = Player.getInstance();
        getDB = false;
        getHeading = false;
        getOrientation = false;
        isUpdate = false;
        t = new Thread(this);
        t.start();
    }

    @Override
    public void update(Message m) {
        switch (m.topic){
            case "Command":
            commInput = m.payload.toString().split(" ");
            isUpdate = true;
            break;
            case "acc":
            System.out.println("accX: " + m.arrPaylaod[0] + " | accY: " + m.arrPaylaod[1] + " | accZ: " + m.arrPaylaod[2] + " | Timestamp: " + TCP_Client.timeStamp);
            break;
            case "dB":
            System.out.println("Peak: " + m.payload + " | Timestamp: " + TCP_Client.timeStamp);
            break;
            case "heading":
            System.out.println("headingX: " + m.arrPaylaod[0] + " | headingY: " + m.arrPaylaod[1] + " | headingZ: " + m.arrPaylaod[2] + " | Timestamp: " + TCP_Client.timeStamp);
            break;
            case "orientation":
            System.out.println("Orientation: " + m.payload + " | Timestamp: " + TCP_Client.timeStamp);
            break;
        }
    }

    private void tutorial(Player player){
        System.out.println("Welcome to THE LEGEND OF THE SANDWICH!\n" + 
        "This game was made by the collaborative efforts of:\n" +
        "Ahmad Mansour\n"+
        "Baraa Abed\n" +
        "Jawad Zaabalawi\n" + 
        "Mohammed Hani Ahmed\n" +
        "To play the game, press ENTER\nENJOY!");
        scan.nextLine();
        System.out.println("You wake up one day, doing what you do every time. You take your daily shower, brush your teeth, and get on with your day." + 
        "Soon, it's 2 PM in the afternoon, and you are STARVING! Maybe you can check what your fridge has for you today.\n" +
        "(Hint: Type \"use fridge\")");
        while(!(commInput[0].equalsIgnoreCase("use") && commInput[1].equalsIgnoreCase("fridge")));
        System.out.println("You opened the fridge. There you find 2 peices of what you assume is bread? It looks like bread, feels like bread, and smells like bread" +
        "However, the color of this \'bread\' is golden. The problem is that you don't remember seeing this bread in your fridge before."+
        "Not only that, but it seems like there is some kind of note right under one of the pieces. All you understand from this note though is the first 2 words:"+
        "\"LEGENDARY SANDWICH\". Everything else seems like gibberish to you. It might be a good idea for you to go ask your friend Bob, who works in teh super market nearby." +
        "He was always the type of guy who can answer any question you throw at him.\n"+
        "(Hint: type \"go to Road\"");
        while(!(commInput[0].equalsIgnoreCase("go") && commInput[1].equalsIgnoreCase("to") && commInput[2].equalsIgnoreCase("Road")));
        Player.currentLocation = Road.getInstance();
        System.out.println("It seems like you haven't been out in a while, since can't remember the way to the super market. Maybe you should stop ordering you groceries from online every now and then.\n" + 
        "(Hint: type \"nearby\")");
        while(!(commInput[0].equalsIgnoreCase("nearby")));
        player.checkNearby();
        System.out.println("(Hint: type \"go to Supermarket\")");
        while(!(commInput[0].equalsIgnoreCase("go") && commInput[1].equalsIgnoreCase("to") && commInput[2].equalsIgnoreCase("Supermarket")));
        Player.currentLocation = Supermarket.getInstance();
        System.out.println("You enter the Supermarket and find Bob the cahsier. Thankfully, he seems to be free at the moment. Maybe you should call him for a samll chat?\n" +
        "(Hint: type \"talk to Bob\")");
        while(!(commInput[0].equalsIgnoreCase("Talk") && commInput[1].equalsIgnoreCase("to") && commInput[2].equalsIgnoreCase("Bob"))); 
        System.out.println("Bob: Look who we have here. Seems like you have something to ask me, right? Or else, you would've never left your house.\n"+
        "You explain what you found and he seems very interested. He tells you to leave it to him and come back in a second.\n" +
        "(You have completed the tutorial, to continue with the game, interact with Bob again)");
        while(!(commInput[0].equalsIgnoreCase("Talk") && commInput[1].equalsIgnoreCase("to") && commInput[2].equalsIgnoreCase("Bob"))); 
        player.nextState();
    }








    @Override
    public void run() {
        Player.currentLocation = House.getInstance();
        tutorial(player);
        while(true){
            switch(commInput[0]){
                case "go":
                    if(commInput[1].equalsIgnoreCase("to")){
                        for(int x = 0; x < player.nearby.size(); x++){
                            if(player.nearby.get(x).toString().equalsIgnoreCase(commInput[2])){
                                Player.currentLocation = player.nearby.get(x);
                                player.updateNearby();
                                System.out.println("location updated");
                            }
                        }
                    }
                    break;
            }
        }
        

    }

    
}
