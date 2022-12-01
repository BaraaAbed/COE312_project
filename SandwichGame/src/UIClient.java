import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class UIClient extends ConcreteObserver implements Runnable{
    //initializing variables
    public static boolean fighting;
    public static Enemy fightingEnemy;
    public Thread t;
    private static String[] commInput;
    private static boolean isUpdate;
    public static boolean getAcc;
    public static boolean getDB;
    public static boolean getHeading;
    public static boolean getOrientation;
    public static boolean getGyro;
    private Player player;
    private Scanner scan;
    private Random rand;
    private static UIClient instance;
    public static boolean failedSabo;
    private double dodgeDuration;
    private Location prevLoc;
    

    //constructor
    private UIClient(ArrayList<ConcreteSubject> subjects){
        super(subjects);
        failedSabo = true;
        rand = new Random(System.currentTimeMillis());
        commInput = "lol".split(" ");
        getAcc = false;
        scan = new Scanner(System.in);
        player = Player.getInstance();
        getDB = false;
        getHeading = false;
        getOrientation = false;
        isUpdate = false;
        dodgeDuration = 1.0;
        t = new Thread(this);
        t.start();
    }

    //gets instance (for singleton)
    public static synchronized UIClient getInstance(ArrayList<ConcreteSubject> subjects){
        if(instance == null) instance = new UIClient(subjects);
        return instance;
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

    // getter for commInput (used in bob talk function)
    public static String[] getCommInput() throws InterruptedException{
        while(!isUpdate){
            Thread.sleep(100);
        }
        isUpdate = false;
        return commInput;
    }

    //fight function
    private void fight(Player player) throws InterruptedException{
        switch(fightingEnemy.toString()){
            case "Gordon":
            System.out.println("Gordon: Welcome to Heck's Kitchen! Today, we will have a cook off between the two teams, Team Red and Team blue!"
            + " There are rules that must be followed during this competiton.\n"
            +"Rule 1: Don't ever touch the LAMB SAUCE!\n"
            +"Rule 2: Don't ever touch the LAMB SAUCE!\n"
            +"Rule 3: Don't ever touch the LAMB SAUCE!\n"
            + "Gordon: To make sure you follow the rules, I will personally be guarding the LAMB SAUCE myself.\n"
            + "As you are listening to his rules, you start to ponder on how to get that sauce. That's when you noticed three stations:\n"
            + "Station A: This has an Oven\n"
            + "Station B: This has a Fryer\n"
            + "Station C: This has a Blender\n"
            + "Maybe if you can sabotage one of those stations, you could distract Gordon Ramsey for long enough to silently steal the LAMB SAUCE.\n"
            + "You: Hmm... Lets use a super cool name for this operation. How about Operation: THAT'S MY SAUCE! Yup, my naming sense is the best.");
            HeckItems oven = (HeckItems) Player.currentLocation.items.get(0);
                HeckItems fryer = (HeckItems) Player.currentLocation.items.get(1);
                HeckItems blender = (HeckItems) Player.currentLocation.items.get(2);
                oven.setState(new NotSabotaged());
                fryer.setState(new NotSabotaged());
                blender.setState(new NotSabotaged());
            while(!(oven.getState() instanceof Sabotaged && blender.getState() instanceof Sabotaged && fryer.getState() instanceof Sabotaged) && failedSabo){
                failedSabo = false;
                if(oven.getState() instanceof Sabotaged || blender.getState() instanceof Sabotaged || fryer.getState() instanceof Sabotaged) System.out.println("You still have a chance, try another station!");
                System.out.println("Choose between stations A, B, or C to start the operation:");
                while(!isUpdate){
                    Thread.sleep(100);
                }
                isUpdate = false;
                switch(commInput[0].toUpperCase()){
                    case "A":
                    if(oven.getState() instanceof Sabotaged){
                        oven.printStatus();
                        failedSabo = true;
                        continue;
                    } else {
                        oven.sabotage();
                        if(failedSabo) {
                            System.out.println("You have failed the sabotage!");
                            oven.nextState();
                        }
                    }
                    break;
                    case "B":
                    if(fryer.getState() instanceof Sabotaged){
                        fryer.printStatus();
                        failedSabo = true;
                        continue;
                    } else {
                        fryer.sabotage();
                        if(failedSabo) {
                            System.out.println("You have failed the sabotage!");
                            fryer.nextState();
                        }
                    }
                    break;
                    case "C":  
                    if(blender.getState() instanceof Sabotaged){
                        blender.printStatus();
                        failedSabo = true;
                        continue;
                    } else {
                        blender.sabotage();
                        if(failedSabo) {
                            System.out.println("You have failed the sabotage!");
                            blender.nextState();
                        }
                    }
                    
                    break;
                    default:
                    System.out.println("Ha? How about you try again.");
                    failedSabo = true;
                    continue;
                }
                if(!failedSabo){
                    System.out.println("Gordon Ramsey has went to check on the station, take this chance to SILENTLY sneak behind him and steal the sauce!");
                    Thread.sleep(3000);
                    failedSabo = !TCP_Client.peakSoundBelowThreshold(10, -25);
                    if(failedSabo) System.out.println("You were not quiet enough, and was noticed by Gordon. Thankfully, he mistunderstands it as you running away from the station to not take the blame.");
                }
            }
            if (!failedSabo){
                    System.out.println("You got the sauce, now quickly run away before Gordon catches you!");
                    Thread.sleep(1500);
                    failedSabo = !TCP_Client.avgAccAboveThreshold('X', 5, 3.5);
            }
            
            if (!failedSabo){
                System.out.println("You successfully got the LAMB SAUCE!");
                player.addCoins(10);
                System.out.println("You found a LEGENDARY INGREDIENT, you got 10 coins.");
                player.addItem(Player.currentLocation.items.get(3));
                Player.currentLocation.items.remove(3);
                Player.currentLocation = Road.getInstance();
                player.updateNearby();
                Kitchen.getInstance().blocked = true;
            } else {
                System.out.println("The operation is a bust. Gordon catches on to your act and sends knife straight to your forehead."
                + " It's too late to dodge, and it pierces straight into your head. He then comes to you, grabs two pieces of bread, places your head in between."
                + " He then asks you:\n"
                + "Gordon: WHAT ARE YOU!\n"
                + "For some reason, only one phrase appears in your mind at that moment, and so you shout before blacking out:\n"
                + "You: AN IDIOT SANDWICH!\n"
                + "You then slowely die. Die as an idiot sandwich.");
                player.death();
            }
            break;
            case "Gnome":
            int counter = 0;
            ArrayList<Integer> paths = new ArrayList<Integer>();
            for (int x = 0; x < 10; x++){
                paths.add(rand.nextInt(2));
            }
            System.out.println("You are just about approach the gnome when all of a sudden you hear:\n"
            +"Welcome to the path of the GNOME! As you can see, there are two paths up ahead. The rules are simple, you have to choose the path that doesn't have a gnome waiting there." 
            +" To reach the magic mushroom at the end of the paths, you need to avoid the gnome atleast six times. Otherwise, you will die. The path of the gnome is randomized, so good luck, beacuse you will need it.");
            try{
                for(int x = 0; x < paths.size() && counter < 5; x++){
                    isUpdate = false;
                    System.out.println("There are two paths ahead, choose right or left.");
                    while(!(commInput[0].equalsIgnoreCase("right") || commInput[0].equalsIgnoreCase("left"))){ 
                        Thread.sleep(100);
                    }
                    if((commInput[0].equalsIgnoreCase("right") && paths.get(x) == 1) || (commInput[0].equalsIgnoreCase("left") && paths.get(x) == 0)){
                        System.out.println("You walk through your chosen path nervously. Thankfully, it seems you lucked out.");
                    }
                    else if(counter < 4){
                        System.out.println("You walk through your chosen path nervously. Just as you thought you got lucky, something hits you in the back with the strength of a bull, leaving you with a pretty bad injury."
                        +" Thankfully, it also pushed you forward, where you coincidentally landed at the end of the path. You look behind you with lingering fear. However, it doesn't seem like the gnome is going to chase you... yet." 
                        + " You should be careful next time, you won't always be this lucky.");
                        counter++;
                    } else { //counter == 4
                        System.out.println("You walk through your chosen path nervously. Just as you thought you got lucky, something hits you in the back with the strength of a bull, leaving you with pretty bad injury. "
                        + "This is the third time, and you can feel it is the last as well. The accumulation of injuries is just too much for you, and you start to lose consiousness as you can barely keep your eyes open. " +
                        "Just when when you were about to close your eyes, you hear a sound that will haunt you even after death.\n" +
                        "You have been gnomed to death." );
                        player.death();
                        isUpdate = false;
                        counter++;
                    }
                    commInput[0] = "";
                }
                if (counter < 5){
                    System.out.println("You finally crossed the paths, and you can see the magic mushroom right there. Just when you were worrying about how to leave, you suddenly notice that there is only one path behind you."
                    +" Not only that, but it seems to be connected directly to the exit. That means you won't have to rely on luck to leave this place, which makes you sigh in relief.");
                    Forest.getInstance().pathCrossed = true;
                    Mushroom.getInstance().takable = true;
                    isUpdate = false;
                }
            } catch (InterruptedException e) {}
            break;
            default:
            System.out.println("You have started a fight with the " + fightingEnemy.toString());
            while(player.getHealth() > 0.0 && fightingEnemy.getHealth() > 0.0){
                Thread.sleep(2000);
                System.out.println("You are on the offense, swing your sword to attack the " + fightingEnemy + "!");
                player.attack(TCP_Client.getAvgAcc(5));
                if (fightingEnemy.getHealth() > 0) {
                    System.out.println(fightingEnemy + " is attacking! Get ready to dodge:");
                    Thread.sleep(2000);
                    boolean failed = false;
                    for (int x = rand.nextInt(5,10); (x > 0) && !failed; x--) {
                        switch(rand.nextInt(5)) {
                            case 0:
                            System.out.print("Duck! ");
                            failed = !(TCP_Client.minAccBelowThreshold('Y', dodgeDuration, -2.0));
                            break;
                            case 1:
                            System.out.print("Jump! ");
                            failed = !(TCP_Client.peakAccAboveThreshold('Y', dodgeDuration, 2.0));
                            break;
                            case 2:
                            System.out.print("Move right! ");
                            failed = !(TCP_Client.peakAccAboveThreshold('X', dodgeDuration, 2.0));
                            break;
                            case 3:
                            System.out.print("Move left! ");
                            failed = !(TCP_Client.minAccBelowThreshold('X', dodgeDuration, -2.0));
                            break;
                            case 4:
                            System.out.print("Step back! ");
                            failed = !(TCP_Client.peakAccAboveThreshold('Z', dodgeDuration, 2.0));
                            break;
                        }
                        Thread.sleep(2000);
                        if (!failed) System.out.println("✓");
                        else System.out.println("X");
                    }
                    if (!failed) System.out.println("Good dodging! You dodged the " + fightingEnemy + "'s attack succesfully!");
                    else {
                        System.out.println("Not good. You moved directly into the " + fightingEnemy + "'s attack!");
                        fightingEnemy.attack();
                    }
                }
            }
        }
    }

    //tutorial
    private void tutorial(Player player) throws InterruptedException{
        System.out.println("Welcome to THE MYTH OF THE LEGENDARY SANDWICH!\n" + 
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
        System.out.println("You enter the Supermarket and find Bob the cashier. Thankfully, he seems to be free at the moment. Maybe you should call him for a samll chat?\n" +
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
        System.out.println("Bob: I translated everything on the note, so here have a look. If you have any questions, I am right here. Also, you are gonna need this." +
        "*Bob hands you a stone sword*"+
        "Bob: After you beat some bosses, come see me again to updgrade the sword.");
        player.nextState();
        isUpdate = true;
    }








    @Override
    public void run() {
        Player.currentLocation = House.getInstance();
        // try {
        //     tutorial(player);
        // } catch (InterruptedException e1) {
        //     e1.printStackTrace();
        // }
        while(true){
            try{
                boolean saved = false;
                if(Player.currentLocation == Warehouse.getInstance() && !(player.getEquipped() instanceof Torch && Torch.on) ){
                    System.out.println("Even though you knew you shouldn't, you still did it. Unsuprisingly, you have been stabbed in the back. 10 times.\n" +
                    "You died. Maybe you should curb your curiosity a little next time.");
                    player.death();
                }
                if(Player.currentLocation == Sewers.getInstance()){
                    if(TCP_Client.avgAccAboveThreshold('X', 5, 3.5)){
                        System.out.println("You have successfully escaped the rats!");
                        if(prevLoc == Road.getInstance()) Player.currentLocation = RatHouse.getInstance();
                        else Player.currentLocation = Road.getInstance();
                        player.updateNearby();
                        System.out.println(Player.currentLocation.getDescription());
                    } else {
                        System.out.println("You are not fast enough, and soon rats catch up to you. There is no escape anymore.\n" 
                        + "You died, becoming dinner for the hungry swarm of rats.");
                        player.death();
                    }
                }
                if(Player.currentLocation == Cave.getInstance() && Cave.getInstance().blocked){
                    if(player.getEquipped() instanceof FireExtinguisher){
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
                    else {
                        Cave.getInstance().blocked = false;
                    }
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
                                            prevLoc = Player.currentLocation;
                                            Player.currentLocation = player.nearby.get(x);
                                            System.out.println(Player.currentLocation.getDescription());
                                        } else System.out.println("It's too dark in there, maybe I should come back when I find a way to light it up.");
                                    }
                                } else {
                                    prevLoc = Player.currentLocation;
                                    Player.currentLocation = player.nearby.get(x);
                                    if(Player.currentLocation == Kitchen.getInstance() && Kitchen.getInstance().blocked){
                                        System.out.println("You probably don't want to see Gordan Ramsey again, especially after stealing his LAMB SAUCE");
                                        Player.currentLocation = Road.getInstance();
                                    } else System.out.println(Player.currentLocation.getDescription());
                                }
                                if(player.nearby.get(x) == Forest.getInstance()) {
                                    Forest.getInstance().pathCrossed = false;
                                    Mushroom.getInstance().takable = false;
                                }
                                player.updateNearby();
                                found = true;
                            }
                        }
                        if (!found) System.out.println("Go where now? I missed what you said there.");
                    } else System.out.println("(Hint: Try \"go to <Location>\"");
                    break;
                    case "nearby":
                    player.checkNearby();
                    break;
                    case "use":
                    for(int x = 0; x < Player.currentLocation.items.size() && !found; x++){
                        if(Player.currentLocation.items.get(x).toString().toLowerCase().equalsIgnoreCase(commInput[1])){
                            Player.currentLocation.items.get(x).use();
                            found = true;
                        } 
                    }
                    if (!found) {
                        if(player.getEquipped() != null && player.getEquipped().toString().equalsIgnoreCase(commInput[1])) player.getEquipped().use();
                        else System.out.println("Use what? I missed what you said there.");
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
                    if (!found) System.out.println("Take what now? I missed what you said there.");
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
                    } else System.out.println("There is no enemy to fight here, how about you go punch the wall instead.");
                    break;
                    case "unequip":
                    player.unequip();
                    break;
                    case "punch":
                    if(commInput[1].equalsIgnoreCase("wall")){
                        System.out.println("We didn't mean in literally... How about you punch your screen?");
                    }
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