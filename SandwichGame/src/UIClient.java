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
    public static boolean getGyro;
    private Player player;
    private Scanner scan;
    private Random rand;
    

    //constructor
    public UIClient(ArrayList<ConcreteSubject> subjects){
        super(subjects);
        rand = new Random(System. currentTimeMillis());
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
            int counter = 0;
            ArrayList<Integer> paths = new ArrayList<Integer>();
            for (int x = 0; x < 5; x++){
                paths.add(rand.nextInt(2));
            }
            System.out.println("You are just about approach the gnome when all of a sudden you hear:\n"
            +"Welcome to the path of the GNOME! As you can see, there are two paths up ahead. The rules are simple, you have to choose the path that doesn't have a gnome waiting there." 
            +"To reach the magic mushroom at the end of the paths, you need to avoid the gnome atleast three times. Otherwise, you will die. The path of the gnome is randomized, so good luck, beacuse you will need it.");
            try{
                for(int x = 0; x < paths.size() && counter < 3; x++){
                    isUpdate = false;
                    System.out.println("There are two paths ahead, choose right or left.");
                    while(!(commInput[0].equalsIgnoreCase("right") || commInput[0].equalsIgnoreCase("left"))){ 
                        Thread.sleep(100);
                    }
                    if((commInput[0].equalsIgnoreCase("right") && paths.get(x) == 1) || (commInput[0].equalsIgnoreCase("left") && paths.get(x) == 0)){
                        System.out.println("You walk through your chosen path nervously. Thankfully, it seems you lucked out.");
                    }
                    else if(counter < 2){
                        System.out.println("You walk through your chosen path nervously. Just as you thought you got lucky, something hits you in the back with the strength of a bull, leaving you with a pretty bad injury."
                        +" Thankfully, it also pushed you forward, where you coincidentally landed at the end of the path. You look behind you with lingering fear. However, it doesn't seem like the gnome is going to chase you... yet." 
                        + " You should be careful next time, you won't always be this lucky.");
                        counter++;
                    } else { //counter == 2
                        System.out.println("You walk through your chosen path nervously. Just as you thought you got lucky, something hits you in the back with the strength of a bull, leaving you with pretty bad injury."
                        + "This is the third time, and you can feel it is the last as well. The accumilation of injuries is just too much for you, and you start to lose consiousness as you can barely keep your eyes open" +
                        "just when when you were about to close your eyes, you hear a sound that will haunt you even after death.\n" +
                        "You have been gnomed to death." );
                        player.death();
                        isUpdate = false;
                        counter++;
                    }
                    commInput[0] = "";
                }
                if (counter < 3){
                    System.out.println("You finally crossed the paths, and you can see the magic mushroom right there. Just when you were worrying about how to leave, you suddenly notice that there is only one path behind you."
                    +" Not only that, but it seems to be connected directly to the exit. That means you won't have to rely on luck to leave this place, which makes you sigh in relief.");
                    Forest.getInstance().pathCrossed = true;
                    Mushroom.getInstance().takable = true;
                    isUpdate = false;
                }
            } catch (InterruptedException e) {}
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
        System.out.println("It seems like you haven't been out in a while, since you can't remember the way to the supermarket. Maybe you should stop ordering your groceries from online every now and then.\n" + 
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
                boolean saved = false;
                if(Player.currentLocation == Warehouse.getInstance() && !(player.getEquipped() instanceof Torch && Torch.on) ){
                    System.out.println("Even though you knew you shouldn't, you still did it. A moment later, you feel something hit you hard in the head. Then, everything went blank.\n" +
                    "You died. Maybe you should curb your curiousity a little next time.");
                    player.death();
                }
                if(Player.currentLocation == Cave.getInstance() && Cave.getInstance().blocked){
                    if( player.getEquipped() instanceof FireExtinguisher){
                        double intitialTime = System.currentTimeMillis();
                        while(System.currentTimeMillis() - intitialTime < 5000.0 && (!(commInput[0].equalsIgnoreCase("use") && commInput[1].equalsIgnoreCase("extinguisher")))){
                            Thread.sleep(100);
                        }
                        if(commInput[0].equalsIgnoreCase("use") && commInput[1].equalsIgnoreCase("extinguisher")) saved = true;
                    }
                    if(!saved) {
                        System.out.println("You have been burnt to death");
                        player.death();
                        isUpdate = false;
                    }
                    else Cave.getInstance().blocked = false;
                }
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
                                if(player.nearby.get(x) == Warehouse.getInstance()){
                                    if(!(player.getEquipped() instanceof Torch)){
                                        System.out.println("It's too dark in there, maybe I should come back when I find a way to light it up.");
                                    } else {
                                        if(Torch.on){
                                            Player.currentLocation = player.nearby.get(x);
                                            System.out.println(Player.currentLocation.getDescription());
                                        } else System.out.println("It's too dark in there, maybe I should come back when I find a way to light it up.");
                                    }
                                } else {
                                    Player.currentLocation = player.nearby.get(x);
                                    System.out.println(Player.currentLocation.getDescription());
                                }
                                if(player.nearby.get(x) == Forest.getInstance()) {
                                    Forest.getInstance().pathCrossed = false;
                                    Mushroom.getInstance().takable = false;
                                }
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
                                if (Player.currentLocation.items.get(x) instanceof Ingredient) {
                                    player.getIngredients().add((Ingredient) Player.currentLocation.items.get(x));
                                    System.out.println("You found a LEGENDARY INGREDIENT, you got 10 coins.");
                                    player.addCoins(10); // ahmad changing later
                                }
                                player.addItem(Player.currentLocation.items.get(x));
                                Player.currentLocation.items.remove(Player.currentLocation.items.get(x));
                            } else System.out.println("This item can't be taken");
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