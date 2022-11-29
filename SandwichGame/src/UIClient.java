import java.util.ArrayList;
import java.util.Random;
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
    private Random rand;
    

    //constructor
    public UIClient(ArrayList<ConcreteSubject> subjects){
        super(subjects);
        rand = new Random();
        commInput = "lol".split(" ");
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
            commInput = (m.payload.toString() + " bla bla bla").split(" ");
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

    //fight funciton
    private void fight(Player player) throws InterruptedException{
        switch(fightingEnemy.toString()){
            case "Gordon":
            //hecks kitchen fight
            break;
            case "Gnome":
            //maze thing
            break;
            default:
            System.out.println("You have started a fight with " + fightingEnemy.toString());
            while(player.getHealth() > 0.0 && fightingEnemy.getHealth() > 0.0){
                System.out.println("You are on the offense, slash your sword to attack him");
                if(TCP_Client.getAvgAcc(3)[0] > 15.0){
                    System.out.println("Your slash hit! " + fightingEnemy.toString() + " has taken damage. Now get ready to dodge!");
                    player.attack();
                }
                Thread.sleep(rand.nextInt(1000,3000));
                System.out.println(fightingEnemy + " is attacking! Dodge quick!");
                //if dodged thingy
            }
        }
    }

    //tutorial
    private void tutorial(Player player) throws InterruptedException{
        System.out.println("Welcome to THE LEGEND OF THE SANDWICH!\n" + 
        "This game was made by the collaborative efforts of:\n" +
        "Ahmad Mansour\n"+
        "Baraa Abed\n" +
        "Jawad Zaabalawi\n" + 
        "Mohammed Hani Ahmed\n" +
        "To play the game, type START\nENJOY!");
        while(!(commInput[0].equalsIgnoreCase("START"))){
            Thread.sleep(100);
        }
        System.out.println("You wake up one day, doing what you do every time. You take your daily shower, brush your teeth, and get on with your day." + 
        "Soon, it's 2 PM in the afternoon, and you are STARVING! Maybe you can check what your fridge has for you today.\n" +
        "(Hint: Type \"use fridge\")");
        while(!(commInput[0].equalsIgnoreCase("use") && commInput[1].equalsIgnoreCase("fridge"))){
            Thread.sleep(100);
        }
        player.addItem(Bread.getInstance());
        Player.currentLocation.items.remove(Bread.getInstance());
        System.out.println("You opened the fridge. There you find 2 peices of what you assume is bread? It looks like bread, feels like bread, and smells like bread" +
        "However, the color of this \'bread\' is golden. The problem is that you don't remember seeing this bread in your fridge before."+
        "Not only that, but it seems like there is some kind of note right under one of the pieces. All you understand from this note though is the first 2 words:"+
        "\"LEGENDARY SANDWICH\". Everything else seems like gibberish to you. It might be a good idea for you to go ask your friend Bob, who works in teh super market nearby." +
        "He was always the type of guy who can answer any question you throw at him.\n"+
        "(Hint: type \"go to Road\")");
        while(!(commInput[0].equalsIgnoreCase("go") && commInput[1].equalsIgnoreCase("to") && commInput[2].equalsIgnoreCase("Road"))){
            Thread.sleep(100);
        }
        Player.currentLocation = Road.getInstance();
        player.updateNearby();
        System.out.println("It seems like you haven't been out in a while, since can't remember the way to the super market. Maybe you should stop ordering your groceries from online every now and then.\n" + 
        "(Hint: type \"nearby\")");
        while(!(commInput[0].equalsIgnoreCase("nearby"))){
            Thread.sleep(100);
        }
        player.checkNearby();
        System.out.println("(Hint: type \"go to Supermarket\")");
        while(!(commInput[0].equalsIgnoreCase("go") && commInput[1].equalsIgnoreCase("to") && commInput[2].equalsIgnoreCase("Supermarket"))){
            Thread.sleep(100);
        }
        Player.currentLocation = Supermarket.getInstance();
        player.updateNearby();
        System.out.println("You enter the Supermarket and find Bob the cahsier. Thankfully, he seems to be free at the moment. Maybe you should call him for a samll chat?\n" +
        "(Hint: type \"talk to Bob\")");
        while(!(commInput[0].equalsIgnoreCase("Talk") && commInput[1].equalsIgnoreCase("to") && commInput[2].equalsIgnoreCase("Bob"))){
            Thread.sleep(100);
        }
        System.out.println("Bob: Look who we have here. Seems like you have something to ask me, right? Or else, you would've never left your house.\n"+
        "You explain what you found and he seems very interested. He tells you to leave it to him and come back in a second.\n" +
        "(You have completed the tutorial, to continue with the game, interact with Bob again)");
        commInput[0] = "bla";
        while(!(commInput[0].equalsIgnoreCase("Talk") && commInput[1].equalsIgnoreCase("to") && commInput[2].equalsIgnoreCase("Bob"))){
            Thread.sleep(100);
        }
        System.out.println("Bob: I translated everything on the note, so here have a look. If youhave any questions, I am right here. Also, you are gonna need this." +
        "After saying that he gave you a stone sword."+
        "Bob: After you beat some bosses, come see me again to updgrade the sword.");
        //player.weapon = new Weapon();
        player.addItem(player.weapon);
        player.nextState();
        isUpdate = true;
    }








    @Override
    public void run() {
        Player.currentLocation = House.getInstance();
        try {
            tutorial(player);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        while(true){
            try{
                while(!isUpdate){
                    Thread.sleep(100);
                }
                isUpdate = false;
                boolean found = false;
                switch(commInput[0].toLowerCase()){
                    case "go":
                    if(commInput[1].equalsIgnoreCase("to")){
                        for(int x = 0; x < player.nearby.size() && !found; x++){
                            if(player.nearby.get(x).toString().equalsIgnoreCase(commInput[2])){
                                Player.currentLocation = player.nearby.get(x);
                                player.updateNearby();
                                found = true;
                            }
                        }
                        if (!found) System.out.println("Go where now? I think missed what you said there.");
                    } else System.out.println("(Hint: Try \"go to <Location>\"");
                    break;
                    case "nearby":
                    player.checkNearby();
                    break;
                    case "use":
                    for(int x = 0; x < Player.currentLocation.items.size() && !found; x++){
                        if(Player.currentLocation.items.get(x).toString().equalsIgnoreCase(commInput[1])){
                            Player.currentLocation.items.get(x).use();
                            found = true;
                        } 
                    }
                    if (!found) {
                        if(player.getEquipped().toString().equals(commInput[1])) player.getEquipped().use();
                        else System.out.println("Use what? I think missed what you said there.");
                    }
                    break;
                    case "look":
                    if(commInput[1].equalsIgnoreCase("around")){
                        player.look();
                    } else System.out.println("(Hint: Try typing \"look around\")");
                    break;
                    case "take":
                    for(int x = 0; x < Player.currentLocation.items.size() && !found; x++){
                        if(Player.currentLocation.items.get(x).toString().equalsIgnoreCase(commInput[1])){
                            if(Player.currentLocation.items.get(x).takable){
                                player.addItem(Player.currentLocation.items.get(x));
                                Player.currentLocation.items.remove(Player.currentLocation.items.get(x));
                            }
                            found = true;
                        } 
                    }
                    if (!found) System.out.println("Take what now? I think missed what you said there.");
                    break;
                    case "inventory":
                    if(!player.isInvEmpty()) player.showInv();
                    else System.out.println("Wow, to be able to break the game and get an empty inventory, I'm impressed."); 
                    break;
                    case "talk":
                    if(commInput[1].equalsIgnoreCase("to")){
                        for(int x = 0; x < Player.currentLocation.npcs.size() && !found; x++){
                            if(Player.currentLocation.npcs.get(x).toString().equalsIgnoreCase(commInput[2])){
                                Player.currentLocation.npcs.get(x).talk();
                                isUpdate = false;
                                found = true;
                            } 
                        }
                    } else System.out.println("(Hint: Try typing \"Talk to <name>\")");
                    break;
                    case "equip": 
                    for(int x = 0; x < player.getInv().size() && !found; x++){
                        if(player.getInv().get(x).toString().equalsIgnoreCase(commInput[1])){
                            player.equip(player.getInv().get(x));
                            found = true;
                        }
                    }
                    if (!found) System.out.println("Equip what now? I missed what you said there.");
                    break;
                    case "fight":
                    fightingEnemy = Player.currentLocation.enemy;
                    if (fightingEnemy != null){
                        fight(player);
                    } else System.out.println("There is no enemy to fight here, how about you go punch a wall instead.");
                    break;
                    case "unequip":
                    player.unequip();
                    break;
                    default:
                    System.out.println("Ha? I thought I heard something. Must've been a fly. Those pesky flies!");
                }
            } catch (InterruptedException e){
                e.printStackTrace();
            } catch (ArrayIndexOutOfBoundsException Ae){
                //do nothing
            } catch (NullPointerException ne){
                //do nothing
            }
                
        }
    }
}