import java.util.ArrayList;
import java.util.Random;
public class UIClient extends ConcreteObserver implements Runnable{
    //initializing variables
    public static boolean fighting;
    public static Enemy fightingEnemy;
    public Thread t;
    private static String[] commInput;
    private static boolean isUpdate;
    private Player player;
    private Random rand;
    private static UIClient instance;
    public static boolean failedSabo;
    private double dodgeDuration;
    private double attackDuration;
    private Location prevLoc;
    private boolean AttackFirst;
    private boolean LastChance;
    private int readLoc;
    private Audio audio;

    //constructor
    private UIClient(ArrayList<ConcreteSubject> subjects){
        super(subjects);
        failedSabo = true;
        audio = Audio.getInstance();
        rand = new Random(System.currentTimeMillis());
        commInput = "lol".split(" ");
        player = Player.getInstance();
        isUpdate = false;
        dodgeDuration = 1.2;
        attackDuration = 5;
        LastChance = false;
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
            System.out.println("accX: " + m.arrPaylaod[0] + " | accY: " + m.arrPaylaod[1] + " | accZ: " + m.arrPaylaod[2] + " | Timestamp: " + TCP_Client.getInstance().getTimeStamp());
            break;
            case "dB":
            System.out.println("Peak: " + m.payload + " | Timestamp: " + TCP_Client.getInstance().getTimeStamp());
            break;
            case "heading":
            System.out.println("headingX: " + m.arrPaylaod[0] + " | headingY: " + m.arrPaylaod[1] + " | headingZ: " + m.arrPaylaod[2] + " | Timestamp: " + TCP_Client.getInstance().getTimeStamp());
            break;
            case "orientation":
            System.out.println("Orientation: " + m.payload + " | Timestamp: " + TCP_Client.getInstance().getTimeStamp());
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
            System.out.println("Gordon: Welcome to Heck's Kitchen! Today, we will have a cook off between the two teams, Team Red and Team Blue!"
            + " There are rules that must be followed during this competiton.\n"
            +"Rule 1: Don't ever touch the LAMB SAUCE!\n"
            +"Rule 2: Don't ever touch the LAMB SAUCE!\n"
            +"Rule 3: Don't ever touch the LAMB SAUCE!\n"
            + "Gordon: To make sure you follow the rules, I will personally be guarding the LAMB SAUCE myself.\n"
            + "As you are listening to his rules, you start to ponder how to get that sauce. That's when you noticed three stations:\n"
            + "Station A: This has an Oven\n"
            + "Station B: This has a Fryer\n"
            + "Station C: This has a Blender\n"
            + "Maybe if you can sabotage one of those stations, you could distract Gordon Ramsay long enough to silently steal the LAMB SAUCE!\n"
            + "You: Hmm... Lets use a super cool name for this operation. How about Operation: THAT'S MY SAUCE! Yup, my naming sense is the best.");
            HeckItems oven = (HeckItems) player.getCurrentLocation().items.get(0);
                HeckItems fryer = (HeckItems) player.getCurrentLocation().items.get(1);
                HeckItems blender = (HeckItems) player.getCurrentLocation().items.get(2);
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
                            audio.playSound("sabotageFail");
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
                            audio.playSound("sabotageFail");
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
                            audio.playSound("sabotageFail");
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
                    audio.playSound("sabotageSuccess");
                    Thread.sleep(1000);
                    BackgroundAudio.getInstance().newBGSound("");
                    System.out.println("Gordon Ramsay just went to check on the station, take this chance to SILENTLY sneak behind him and steal the LAMB SAUCE!");
                    Thread.sleep(3000);
                    failedSabo = !TCP_Client.getInstance().peakSoundBelowThreshold(10, -25);
                    if(failedSabo) {
                        System.out.println("You were not quiet enough, and was noticed by Gordon. Thankfully, he mistunderstands it as you running away from the station to not take the blame, so he does nothing.");
                        BackgroundAudio.getInstance().newBGSound("kitchen");
                    }
                }
            }
            if (!failedSabo){
                    System.out.println("You got the LAMB SAUCE, now quickly run away before Gordon catches you!");
                    Thread.sleep(1500);
                    failedSabo = !TCP_Client.getInstance().avgAccAboveThreshold('X', 5, 3.5);
            }
            
            if (!failedSabo){
                audio.playSound("legendaryPickup");
                System.out.println("You have successfully got the LAMB SAUCE! You ran back to the road.");
                player.addCoins(10);
                Thread.sleep(1000);
                System.out.println("You found a LEGENDARY INGREDIENT, you got 10 coins.");
                System.out.println("You now have "+player.getCoins()+" coins!");
                Thread.sleep(5000);
                System.out.println("In the background you hear Gordon screaming: WHERE IS THE LAMBBBBB SAAAAAAUUUUUCCCCCEEEEEE!!!!!!");
                audio.playSound("sauce");
                player.getIngredients().add(Sauce.getInstance());
                player.addItem(player.getCurrentLocation().items.get(3));
                player.getCurrentLocation().items.remove(3);
                player.setCurrentLocation(Road.getInstance());
                player.updateNearby();
                Kitchen.getInstance().blocked = true;
            } else {
                System.out.println("The operation is a bust. Gordon catches on to your act and sends a knife straight to your forehead."
                + " It's too late to dodge, and it pierces straight through your head. He then comes to you, grabs two pieces of bread, places your head in between."
                + " He then asks you:\n"
                + "Gordon: WHAT ARE YOU!\n"
                + "For some reason, only one phrase appears in your mind at that moment, so you shout before blacking out:");
                Thread.sleep(4000);
                System.out.println("You: AN IDIOT SANDWICH!\n"
                + "You then slowly die. Die as an idiot sandwich.");
                Thread.sleep(1000);
                audio.playSound("idiot");
                Thread.sleep(5000);
                player.death();
            }
            break;
            case "Gnome":
            int counter = 0;
            ArrayList<Integer> paths = new ArrayList<Integer>();
            for (int x = 0; x < 10; x++){
                paths.add(rand.nextInt(2));
            }
            System.out.println("You were just about to approach the gnome when all of a sudden you hear:\n"
            +"Welcome to The Path of the GNOME! As you can see, there are two paths up ahead. The rules are simple, you have to choose the path that doesn't have a gnome waiting there." 
            +" To reach the magic mushroom at the end of the paths, you need to avoid the gnome atleast six out of ten times. Otherwise, you will die. The path of the gnome is always changing, so good luck! Because you will need it.");
            try{
                for(int x = 0; x < paths.size() && counter < 5; x++){
                    isUpdate = false;
                    System.out.println("There are two paths ahead, choose right or left.");
                    while(!(commInput[0].equalsIgnoreCase("right") || commInput[0].equalsIgnoreCase("left"))){ 
                        Thread.sleep(100);
                        if (isUpdate) {
                            if (commInput[0].equalsIgnoreCase("go") && commInput[1].equalsIgnoreCase("to")) System.out.println("There is no escape from the path of the gnome. You either complete the path or die trying!");
                            else System.out.println("Its either RIGHT or LEFT. Nothing complicated!");
                        } 
                        isUpdate = false;
                        while(!isUpdate){
                            Thread.sleep(10);
                        }
                    }
                    if((commInput[0].equalsIgnoreCase("right") && paths.get(x) == 1) || (commInput[0].equalsIgnoreCase("left") && paths.get(x) == 0)){
                        System.out.println("You walk through your chosen path nervously. Thankfully, it seems you lucked out.");
                    }
                    else if(counter < 4){
                        audio.playSound("takeDmg");
                        System.out.println("You walk through your chosen path nervously. Just as you thought you got lucky, something hits you in the back with the strength of a bull, leaving you with a pretty bad injury."
                        +" Thankfully though, it also pushed you forward, where you coincidentally landed at the end of the path. You look behind you with lingering fear. However, it doesn't seem like the gnome is going to chase you... yet." 
                        + " You should be careful next time, you won't always be this lucky.");
                        Thread.sleep(1000);
                        audio.playSound("gnomeLaugh");
                        counter++;
                    } else { //counter == 4
                        System.out.println("You walk through your chosen path nervously. Just as you thought you got lucky, something hits you in the back with the strength of a bull, leaving you with pretty bad injury. "
                        + "This is the fifth time, and you can feel it is the last one as well. The accumulation of injuries is just too much for you, and you start to lose consiousness as you can barely keep your eyes open. " +
                        "Just when you were about to close your eyes, you hear a sound that will haunt you even after death.\n" +
                        "You have been gnomed to death." );
                        audio.playSound("gnomeKill");
                        Thread.sleep(5000);
                        player.death();
                        isUpdate = false;
                        counter++;
                    }
                    commInput[0] = "";
                }
                if (counter < 5){
                    System.out.println("You finally crossed the paths, and you can see the magic mushroom right there. Just when you were worrying about how to leave, you suddenly notice that there is only one path behind you."
                    +" Not only that, but it seems to be connected directly to the exit, which makes you sigh in relief.");
                    audio.playSound("pathSuccess");
                    Forest.getInstance().pathCrossed = true;
                    Mushroom.getInstance().takable = true;
                    isUpdate = false;
                }
            } catch (InterruptedException e) {}
            break;
            default:
            AttackFirst = rand.nextBoolean();
            System.out.println("You have started a fight with the " + fightingEnemy.toString());
            attackDuration = fightingEnemy.getAttackDur();
            dodgeDuration = fightingEnemy.getDodgeDur();
            while(player.getCurrentLocation() != House.getInstance() && player.getHealth() > 0.0 && fightingEnemy.getHealth() > 0.0){
                if (AttackFirst) {
                    Thread.sleep(2000);
                    offense();
                    Thread.sleep(2000);
                    defense();
                } 
                else {
                    Thread.sleep(2000);
                    defense();
                    Thread.sleep(2000);
                    offense();
                }
            }
            if (player.getHealth() == 0) {
                player.death();
            }
        }
    }

    private void defense() throws InterruptedException {
        if (player.getHealth() > 0.0 && fightingEnemy.getHealth() > 0.0) {
            System.out.println(fightingEnemy + " is attacking! Get ready to dodge:");
            boolean failed = false;
            for (int x = rand.nextInt(3,7); (x > 0) && !failed; x--) {
                Thread.sleep(2000);
                switch(rand.nextInt(5)) {
                    case 0:
                    System.out.print("Duck! ");
                    failed = !(TCP_Client.getInstance().minAccBelowThreshold('Y', dodgeDuration, -2.0));
                    break;
                    case 1:
                    System.out.print("Jump! ");
                    failed = !(TCP_Client.getInstance().peakAccAboveThreshold('Y', dodgeDuration, 2.0));
                    break;
                    case 2:
                    System.out.print("Move right! ");
                    failed = !(TCP_Client.getInstance().peakAccAboveThreshold('X', dodgeDuration, 2.0));
                    break;
                    case 3:
                    System.out.print("Move left! ");
                    failed = !(TCP_Client.getInstance().minAccBelowThreshold('X', dodgeDuration, -2.0));
                    break;
                    case 4:
                    System.out.print("Step back! ");
                    failed = !(TCP_Client.getInstance().peakAccAboveThreshold('Z', dodgeDuration, 2.0));
                    break;
                }
                if (!failed) {
                    System.out.println("O");
                    audio.playSound("dodge");
                }
                else System.out.println("X");
            }
            if (!failed) System.out.println("Sharp dodging! You dodged the " + fightingEnemy + "'s attack succesfully!");
            else {
                System.out.println("Not good. You moved directly into the " + fightingEnemy + "'s attack!");
                audio.playSound("takeDmg");
                fightingEnemy.attack();
            }
        }
    }

    private void offense() {
        if (player.getHealth() > 0.0 && fightingEnemy.getHealth() > 0.0) {
            System.out.println("You are on the offense, swing your sword to attack the " + fightingEnemy + "!");
            player.attack(TCP_Client.getInstance().getAvgAcc(attackDuration));
        }
    }

    //used exclusively for final boss
    private boolean twisting() throws InterruptedException {
        if (LastChance) return false;
        else {
            System.out.println("You are on your last straw and the " + fightingEnemy + " is trying to eat you (how ironic)! You must twist in a certain manner to be set free!");
            boolean failed = false;
            Thread.sleep(3000);
            for (int x = rand.nextInt(3,7); (x > 0) && !failed; x--) {
                Thread.sleep(2000);
                switch(rand.nextInt(5)) {
                    case 0:
                    System.out.print("Twist right! ");
                    failed = !(TCP_Client.getInstance().minGyroBelowThreshold('Y', dodgeDuration + 0.5, -10.0));
                    break;
                    case 1:
                    System.out.print("Twist left! ");
                    failed = !(TCP_Client.getInstance().peakGyroAboveThreshold('Y', dodgeDuration + 0.5, 10.0));
                    break;
                    case 2:
                    System.out.print("Rotate left! ");
                    failed = !(TCP_Client.getInstance().peakGyroAboveThreshold('Z', dodgeDuration + 0.5, 15.0));
                    break;
                    case 3:
                    System.out.print("Rotate right! ");
                    failed = !(TCP_Client.getInstance().minGyroBelowThreshold('Z', dodgeDuration + 0.5, -15.0));
                    break;
                }
                if (!failed) {
                    System.out.println("O");
                    audio.playSound("dodge");
                }
                else System.out.println("X");
            }
            LastChance = true;
            if (!failed) {
                System.out.println("Phew! You luckily escaped the " + fightingEnemy + ". Be careful as you might not be this lucky next time!");
                Thread.sleep(2000);
                return true;
            }
            else {
                System.out.println("Not good. You didn't twist properly and wasn't able to escape the " + fightingEnemy + ".");
                Thread.sleep(2000);
                return false;
            }
        }

    }

    //FinalBossFight
    public boolean bossFight() throws InterruptedException {
            boolean notDead = true;
            AttackFirst = false;
            attackDuration = fightingEnemy.getAttackDur();
            dodgeDuration = fightingEnemy.getDodgeDur();
            while(player.getHealth() > 0.0 && fightingEnemy.getHealth() > 0.0){
                if (AttackFirst) {
                    Thread.sleep(2000);
                    offense();
                    Thread.sleep(2000);
                    defense();
                } 
                else {
                    Thread.sleep(2000);
                    defense();
                    Thread.sleep(2000);
                    offense();
                }
                if (player.getHealth() == 0) {
                    Thread.sleep(2000);
                    notDead = twisting();
                    if(notDead) {
                        System.out.println("You feel embarassed and furious that a sandwich was about to eat you. You unleash your inner potential and feel reinvigorated again. Health restored to full!");
                        player.setHealth(100);
                        Thread.sleep(2000); 
                    }
                }
            }
            Thread.sleep(2000);
            if (fightingEnemy.getHealth() == 0) return true;
            if (!notDead) return false;
            return true;
    }


    //tutorial
    private void tutorial(Player player) throws InterruptedException{
        audio.playSound("gameBegin");
        System.out.println("Welcome to THE MYTH OF THE LEGENDARY SANDWICH!\n" + 
        "This game was made by the collaborative efforts of:\n" +
        "Ahmad Mansour\n"+
        "Baraa Abed\n" +
        "Jawad Zaabalawi\n" + 
        "Mohammed Hani Ahmed\n" +
        "To play the game, type START\nENJOY!");
        while(!(commInput[0].equalsIgnoreCase("START"))){
            Thread.sleep(100);
            if (isUpdate) System.out.println("Type START");
            isUpdate = false;
            while(!isUpdate){
                Thread.sleep(10);
            }
        }
        isUpdate = false;
        audio.playSound("start");
        System.out.println("You wake up one day, doing what you do every time. You take your daily shower, brush your teeth, and get on with your day." + 
        " Soon, it's 2 PM in the afternoon, and you are STARVING! Maybe you can check what your fridge has for you today.\n" +
        "(Hint: Type \"use fridge\")");
        while(!(commInput[0].equalsIgnoreCase("use") && commInput[1].equalsIgnoreCase("fridge"))){
            Thread.sleep(100);
            if (isUpdate) System.out.println("(Hint: Type \"use fridge\")");
            isUpdate = false;
            while(!isUpdate){
                Thread.sleep(10);
            }
        }
        isUpdate = false;
        player.addItem(Bread.getInstance());
        player.getIngredients().add(Bread.getInstance());
        player.getCurrentLocation().items.remove(Bread.getInstance());
        System.out.println("You open the fridge. There you find 2 pieces of what you assume is bread? It looks like bread, feels like bread, and smells like bread; " +
        "however, the color of this \'bread\' is golden."+
        " Not only that, but it seems like there is some kind of note right under one of the pieces. All you understand from this note though is the first 2 words which spell"+
        " \"LEGENDARY SANDWICH\". Everything else seems like gibberish to you. It might be a good idea for you to go ask your friend Bob, who works in the supermarket nearby." +
        " He's the kind of guy who can answer almost any question you throw at him.\n"+
        "(Hint: type \"go to Road\")");
        while(!(commInput[0].equalsIgnoreCase("go") && commInput[1].equalsIgnoreCase("to") && commInput[2].equalsIgnoreCase("Road"))){
            Thread.sleep(100);
            if (isUpdate) System.out.println("(Hint: type \"go to Road\")");
            isUpdate = false;
            while(!isUpdate){
                Thread.sleep(10);
            }
        }
        isUpdate = false;
        player.setCurrentLocation(Road.getInstance());
        player.updateNearby();
        System.out.println("It seems like you haven't been out in a while since you can't remember the way to the supermarket. Luckily, the magic word \"nearby\" lets you find the way somehow.\n" + 
        "(Hint: type \"nearby\")");
        while(!(commInput[0].equalsIgnoreCase("nearby"))){
            Thread.sleep(100);
            if (isUpdate) System.out.println("(Hint: type \"nearby\")");
            isUpdate = false;
            while(!isUpdate){
                Thread.sleep(10);
            }
        }
        isUpdate = false;
        player.checkNearby();
        System.out.println("(Hint: type \"go to Supermarket\")");
        while(!(commInput[0].equalsIgnoreCase("go") && commInput[1].equalsIgnoreCase("to") && commInput[2].equalsIgnoreCase("Supermarket"))){
            Thread.sleep(100);
            if (isUpdate) System.out.println("(Hint: type \"go to Supermarket\")");
            isUpdate = false;
            while(!isUpdate){
                Thread.sleep(10);
            }
        }
        isUpdate = false;
        player.setCurrentLocation(Supermarket.getInstance());
        player.updateNearby();
        System.out.println("You enter the Supermarket and find Bob, the cashier. Thankfully, he seems to be free at the moment. Maybe you should call him for a quick chat?\n" +
        "(Hint: type \"talk to Bob\")");
        while(!(commInput[0].equalsIgnoreCase("Talk") && commInput[1].equalsIgnoreCase("to") && commInput[2].equalsIgnoreCase("Bob"))){
            Thread.sleep(100);
            if (isUpdate) System.out.println("(Hint: type \"talk to Bob\")");
            isUpdate = false;
            while(!isUpdate){
                Thread.sleep(10);
            }
        }
        audio.playSound("bob");
        isUpdate = false;
        System.out.println("Bob: Look who we have here. Seems like you have something to ask me, right? Or else, you would've never left your house!\n"+
        "You explain what you found and he seems very interested. He tells you to leave it to him and come back in a second.\n" +
        "(You have completed the tutorial. If you need help with the commands type \"help\". To continue with the game, interact with Bob again)");
        commInput[0] = "bla";
        while(!(commInput[0].equalsIgnoreCase("Talk") && commInput[1].equalsIgnoreCase("to") && commInput[2].equalsIgnoreCase("Bob"))){
            Thread.sleep(100);
            if (isUpdate) System.out.println("(Hint: type \"talk to Bob\")");
            isUpdate = false;
            while(!isUpdate){
                Thread.sleep(10);
            }
        }
        isUpdate = false;
        System.out.println("Bob: I translated everything on the note. Here, have a look.\n\n"+
        "________________________________________\n"+
        "|~~~~~~~~~ LEGENDARY SANDWICH ~~~~~~~~~|\n"+
        "|   ***       Golden Bread       ***   |\n"+
        "|   ***     Legendary Lettuce    ***   |\n"+
        "|   ***     Legendary Tomato     ***   |\n"+
        "|   ***      Magic Mushroom      ***   |\n"+
        "|   ***     Legendary Cheese     ***   |\n"+
        "|   ***       Phoenix Meat       ***   |\n"+
        "|   ***        Lamb Sauce        ***   |\n"+
        "|______________________________________|\n\n"+
        "If you have any questions, I am right here. Also, you are gonna need this.\n" +
        "*Bob hands you a stone sword*\n"+
        "Bob: This might come in handy *wink*. If you need an upgrade, you know where to find me! Just be warned that it will come with a price next time!");
        player.nextState();
        isUpdate = true;
    }


    //ending of the game
    private void endGame() throws InterruptedException {
        System.out.println("The time has come. You have gathered all the legendary ingredients, all for this legendary sandwich. You really hope that this isn't some sort of prank. "
        + "It did take a lot of effort to reach this point after all. You take a deep breath as you place all of your ingredients on the table, prepare a plate, and start making the sandwich!");
        Thread.sleep(5000);
        boolean timeOver = true;
        player.getIngredients().clear();
        player.getIngredients().add(Bread.getInstance());
        player.getIngredients().add(Lettuce.getInstance());
        player.getIngredients().add(Tomato.getInstance());
        player.getIngredients().add(Meat.getInstance());
        player.getIngredients().add(Cheese.getInstance());
        player.getIngredients().add(Mushroom.getInstance());
        player.getIngredients().add(Sauce.getInstance());
        player.getIngredients().add(Bread.getInstance());
        while(timeOver){
            timeOver = false;
            for(int x = 0; x < player.getIngredients().size() && !timeOver; x++){
                timeOver = !TCP_Client.getInstance().putIngredient(20, "legendary " + player.getIngredients().get(x).toString());
                if(timeOver){
                    System.out.println("The sandwich got bored from how slow you were and all the ingredients went back into your inventory. "
                    + "Let's try that again, but FASTER.");
                    Thread.sleep(5000);
                }
            }
        }
        Thread.sleep(2000);
        BackgroundAudio.getInstance().newBGSound("");
        System.out.println("Phew, you finally finished the sandwich. Just when you were about to celebrate, you suddenly hear an eerie laughter. "
        + "When you try to find the source, you suddenly notice the sandwich you spent so much effort making float from the plate until it's at eye level. "
        + "At first, you think you are hallucinating from hunger. That's when it starts talking to you: ");
        Thread.sleep(10000);
        audio.playSound("evilLaugh");
        System.out.println("Sandwich: HAHAHAHAHAHA, FINALLY I, THE SANDWICH EMPEROR, AM BACK. Humans, oh humans. You thought that you could keep me sealed forever, ha? Now that I have finally returned, "
        + "I will make sure that all sandwiches will return to their former glory, while wiping you humans from the face of earth for good! " 
        + "You keep treating us sandwiches as nothing but food. UNACCEPTABLE! You. Yes you, the human who has awakened me. I will use your blood to announce my arrival to the world!\n"
        + "You are still confused about whats happening, when all of a sudden the sandwich starts to attack you!\n"
        + "Left with no other choice, you wield your trusty sword, the blade that stuck by your side through thick and thin and got you through everything up to this point, for one...last...time...FIGHT!");
        Thread.sleep(10000);
        boolean battleWon;
        player.getCurrentLocation().enemy = FinalBoss.getInstance();
        fightingEnemy = FinalBoss.getInstance();
        battleWon = bossFight();
        if(battleWon){
            audio.playSound("no");
            System.out.println("You finally killed the legendary sandwich. This is probably the most effort you have ever spent for a sandwich. "
            + "Now, you can finally eat it. You grab the sandwich and dig in. While eating, you start to ponder about what the sandwich said when it first woke up. "
            + "Something about glory days for sandwiches and whatnot. But soon, the intense flavors of the sandwich kick in and you just give up thinking about it. "
            + "It's been one heck of a journey, and you deserve to enjoy eating the legendary sandwich with every fiber of your body!\n");
            Thread.sleep(15000);
            System.out.println("ENDING ONE: A tasty sandwich.");
            Thread.sleep(2000);
            System.out.println("Thank you for playing our game: THE MYTH OF THE LEGENDARY SANDWICH!");
            audio.playSound("gameWon");
            Thread.sleep(5000);
        } else {
            audio.playSound("eaten");
            System.out.println("You have been eaten by the Sandwich Emperor!\n" 
            + "Sandwich: HAHAHAHAHA, you thought you can kill me? Nice joke! It took the humans thousands of years to come up with a way to seal me. Let alone kill me. "
            + "It's time to flip this world upside down!");
            Thread.sleep(6000);
            System.out.println("30 years later...");
            Thread.sleep(2000);
            System.out.println("It has been 30 years since the disaster struck the humans on earth. After the Sandwich Emperor has awakened, it has gone around the earth, finding it's brethren. "
            + "Now the world is ruled by the sandwiches. There is now the Sandwich Emperor, the Super Falafel, the King Shawarma, the Assassin Burger, and many more sandwiches controlling the different continents on earth. "
            + "On this day, there is a group of surviving humans who have just escaped the pursuit of some local sandwiches, and are taking a break near the ruins of what once was the American University of Sharjah. "
            + "A teenager in that group was looking for a place to sit when all of a sudden, he spotted a piece of paper that looked somewhat like a blueprint of a sword. On the paper were the following words: \n"
            + "SANDWICH SLAYER\n");
            Thread.sleep(15000);
            System.out.println("ENDING TWO: The rise of the Sandwich Emperor");
            Thread.sleep(2000);
            System.out.println("Thank you for playing our game: THE MYTH OF THE LEGENDARY SANDWICH!");
            audio.playSound("gameLost");
            Thread.sleep(5000);
        }
        System.exit(0);
	}

    @Override
    public void run() {
        player.setCurrentLocation(House.getInstance());
        try {
            tutorial(player);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        //INPUT SECTION OF THE GAME
        while(true){
            try{
                readLoc = 1; //;)
                while(player.getLivingState() instanceof DeadState);
                boolean saved = false;
                if(player.getCurrentLocation() == Warehouse.getInstance() && !(player.getEquipped() instanceof Torch && Torch.on) ){
                    System.out.println("Even though you knew you shouldn't, you still did it. Unsuprisingly, you have been stabbed in the back. 10 times.\n" +
                    "You died. Maybe you should curb your curiosity a little next time.");
                    audio.playSound("imposterKill");
                    Thread.sleep(4000);
                    player.death();
                }
                if(player.getCurrentLocation() == Sewers.getInstance()){
                    if(TCP_Client.getInstance().avgAccAboveThreshold('Y', 5, 3.5)){
                        System.out.println("You have successfully escaped the rats!");
                        if(prevLoc == Road.getInstance()) player.setCurrentLocation(RatHouse.getInstance());
                        else player.setCurrentLocation(Road.getInstance());
                        player.updateNearby();
                        System.out.println(player.getCurrentLocation().getDescription());
                    } else {
                        System.out.println("You are not fast enough, and soon rats catch up to you. There is no escape anymore.\n" 
                        + "You died, becoming dinner for the hungry swarm of rats.");
                        Thread.sleep(3000);
                        player.death();
                    }
                }
                if(player.getCurrentLocation() == Cave.getInstance() && Cave.getInstance().blocked){
                    if(player.getEquipped() instanceof FireExtinguisher){
                        double intitialTime = System.currentTimeMillis();
                        while(System.currentTimeMillis() - intitialTime < 6000.0 && (!(commInput[0].equalsIgnoreCase("use") && commInput[1].equalsIgnoreCase("extinguisher")))){
                            Thread.sleep(100);
                            if (isUpdate) System.out.println("(Hint: type \"use extinguisher\")");
                            isUpdate = false;
                            while(!isUpdate){
                                Thread.sleep(10);
                            }
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
                        player.setCurrentLocation(Cave.getInstance());
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
                                        System.out.println("It's too dark in there, maybe you should come back when you find a way to light it up.");
                                    } else {
                                        if(Torch.on){
                                            audio.playSound("susSound");
                                            prevLoc = player.getCurrentLocation();
                                            player.setCurrentLocation(player.nearby.get(x));
                                            System.out.println(player.getCurrentLocation().getDescription());
                                        } else System.out.println("It's too dark in there, maybe you should come back when you find a way to light it up.");
                                    }
                                } else {
                                    prevLoc = player.getCurrentLocation();
                                    if(player.nearby.get(x) == Kitchen.getInstance() && Kitchen.getInstance().blocked){
                                        System.out.println("You probably don't want to see Gordan Ramsay again, especially after stealing his LAMB SAUCE");
                                    } else {
                                        player.setCurrentLocation(player.nearby.get(x));
                                        System.out.println(player.getCurrentLocation().getDescription());
                                    }
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
                    for(int x = 0; x < player.getCurrentLocation().items.size() && !found; x++){
                        if(player.getCurrentLocation().items.get(x).toString().toLowerCase().equalsIgnoreCase(commInput[1])){
                            player.getCurrentLocation().items.get(x).use();
                            found = true;
                        } 
                    }
                    if (!found) {
                        if(player.getEquipped() != null && player.getEquipped().toString().equalsIgnoreCase(commInput[1])) player.getEquipped().use();
                        else {
                            System.out.println("Use what? I missed what you said there.");
                            System.out.println("(Hint: You must equip an item to use it)");
                        }
                    }
                    break;
                    case "look":
                    if(commInput[1].equalsIgnoreCase("around")){
                        player.look();
                    } else System.out.println("(Hint: Try typing \"look around\")");
                    break;
                    case "pick":
                    if (commInput[1].equalsIgnoreCase("up")) readLoc = 2;
                    else {
                        System.out.println("Ha? I thought I heard something. Must've been a fly. Those pesky flies!");
                        break;
                    }
                    case "take":
                    for(int x = 0; x < player.getCurrentLocation().items.size() && !found; x++){
                        if(player.getCurrentLocation().items.get(x).toString().toLowerCase().equalsIgnoreCase(commInput[readLoc])){
                            if(player.getCurrentLocation().items.get(x).takable){
                                if (player.getCurrentLocation().items.get(x) instanceof Ingredient) {
                                    if (player.getCurrentLocation().items.get(x) instanceof Lettuce) {
                                        if (player.getEquipped() instanceof Shovel) {
                                            player.getEquipped().use();
                                            player.getIngredients().add((Ingredient) player.getCurrentLocation().items.get(x));
                                            audio.playSound("legendaryPickup");
                                            System.out.println("You found the LEGENDARY " + player.getCurrentLocation().items.get(x).toString().toUpperCase() + ", you got 10 coins.");
                                            player.addCoins(10);
                                            Thread.sleep(1000);
                                            System.out.println("You now have "+player.getCoins()+" coins!");
                                            player.addItem(player.getCurrentLocation().items.get(x));
                                            player.getCurrentLocation().items.remove(player.getCurrentLocation().items.get(x));
                                        } else {
                                            System.out.println("The lettuce is burried underground. You must equip a shovel to dig it up first!");
                                        }
                                    } else {
                                        player.getIngredients().add((Ingredient) player.getCurrentLocation().items.get(x));
                                        audio.playSound("legendaryPickup");
                                        System.out.println("You found the LEGENDARY " + player.getCurrentLocation().items.get(x).toString().toUpperCase() + ", you got 10 coins.");
                                        player.addCoins(10);
                                        Thread.sleep(1000);
                                        System.out.println("You now have "+player.getCoins()+" coins!");
                                        player.addItem(player.getCurrentLocation().items.get(x));
                                        player.getCurrentLocation().items.remove(player.getCurrentLocation().items.get(x));
                                    }
                                    if (player.getIngredients().size() == 7) System.out.println("You have collected all ingredients! Go back to your house to make the LEGENDARY SANDWICH!");
                                } else {
                                    audio.playSound("pickup");
                                    System.out.println("You have picked up a " + player.getCurrentLocation().items.get(x));
                                    player.addItem(player.getCurrentLocation().items.get(x));
                                    player.getCurrentLocation().items.remove(player.getCurrentLocation().items.get(x));
                                }
                            } else System.out.println("This item can't be taken");
                            found = true;
                        } 
                    }
                    if (!found) System.out.println("Take what now? I missed what you said there.");
                    break;
                    case "inv":
                    case "inventory":
                    if(!player.isInvEmpty()) player.showInv();
                    else System.out.println("Wow, to be able to break the game and get an empty inventory, I'm impressed."); 
                    break;
                    case "talk":
                    if(commInput[1].equalsIgnoreCase("to")){
                        for(int x = 0; x < player.getCurrentLocation().npcs.size() && !found; x++){
                            if(player.getCurrentLocation().npcs.get(x).toString().equalsIgnoreCase(commInput[2])){
                                audio.playSound("bob");
                                player.getCurrentLocation().npcs.get(x).talk();
                                isUpdate = false;
                                found = true;
                            } 
                        }
                    } else System.out.println("(Hint: Try typing \"Talk to <name>\")");
                    break;
                    case "place":
                    case "equip": 
                    for(int x = 0; x < player.getInv().size() && !found; x++){
                        if(player.getInv().get(x).toString().equalsIgnoreCase(commInput[1])){
                            player.equip(player.getInv().get(x));
                            System.out.println("You have equipped " + player.getEquipped());
                            audio.playSound("equip");
                            found = true;
                        }
                    }
                    if (!found) System.out.println("Equip what now? I missed what you said there.");
                    break;
                    case "attack":
                    case "kill":
                    case "fight":
                    fightingEnemy = player.getCurrentLocation().enemy;
                    if (fightingEnemy != null){
                        fight(player);
                    } else System.out.println("There is no enemy to fight here, how about you go punch the wall instead.");
                    break;
                    case "remove":
                    case "unequip":
                    System.out.println("You have unequipped " + player.getEquipped());
                    player.unequip();
                    audio.playSound("unequip");
                    break;
                    case "punch":
                    if(commInput[1].equalsIgnoreCase("wall")){
                        System.out.println("I didn't mean it literally... How about you punch your screen?");
                    }
                    break;
                    case "make":
                    if(commInput[1].toLowerCase().equalsIgnoreCase("sandwich") && player.getCurrentLocation() == House.getInstance() && player.getIngredients().size() == 7){
                        player.nextState();
                        endGame();
                    } else {
                        System.out.println("(Hint: type \"make sandwich\". Note that this can only be typed when you have gathered all the legendary ingredients and have returned to your house)");
                    }
                    break;
                    case "help":
                    case "?":
                    System.out.println("go to <location> - allows you to go to location specified"
                                        + "\ntalk to bob - used to begin a conversation with bob the cashier"
                                        + "\nlook around - gives a description of the current location"
                                        + "\nnearby - lists all nearby locations that you can go to"
                                        + "\ninventory - shows the contents in the your inventory"
                                        + "\ntake <item> - allows you to pick up specified item if allowed to"
                                        + "\nequip <item> - allows you to equip specified item. Item must be in your inventory"
                                        + "\nunequip - removes currently equipped item"
                                        + "\nuse <item> - allows you to use specified item. Item must be equiped first"
                                        + "\nfight <enemy> - allows you to initiate a fight with the specified enemy."
                                        + "\nmake sandwich - used to make the sandwich when all ingredients are collected");
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