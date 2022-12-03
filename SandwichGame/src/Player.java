import java.util.ArrayList;
import java.util.Random;

public class Player {
    //initialzing variables
    private Location currentLocation;
    private double health;
    public ArrayList<Location> nearby;
    private ArrayList<Item> inventory;
    private Item equipped;
    public String sword;
    public Weapon weapon; // strategy design pattern
    private ArrayList<Ingredient> ingredients;
    private double dmg;//base dmg
    private static Player instance;//singleton
    private State locationLockState; // state design pattern
    private int coins; // keep track of num of coins
    private double dBThreshold;
    private Random rand;
    private State livingState; // state design pattern

    //Constructor
    private Player(){
        sword = "Stone sword";
        nearby = new ArrayList<Location>();
        inventory = new ArrayList<Item>();
        rand = new Random(System.currentTimeMillis());
        ingredients = new ArrayList<Ingredient>();
        currentLocation = House.getInstance();
        weapon = new LowDamageWeapon(); // starts with stone sword from cashier
        health = 100.0; //change health in death function if you change this
        dmg = 5.0;
        dBThreshold = -0.75;
        updateNearby();
        locationLockState = new MapLockedState();
        livingState = new AliveState();
    }

    //gets instance (for singleton)
    public static synchronized Player getInstance(){
        if(instance == null) instance = new Player();
        return instance;
    }

    //currentLocation getter
    public Location getCurrentLocation(){
        return currentLocation;
    }

    //currentLocation setter
    public void setCurrentLocation(Location l){
        currentLocation = l;
    }

    //health getter
    public double getHealth(){
        return health;
    }

    //health setter
    public void setHealth(double newhealth){
        health = newhealth;
    }

    //take dmg
    public void takeDmg(double _dmg){
        health -= _dmg;
        if (health < 0) {
            health = 0;
        }
        else {
            System.out.println("You now have " + health + " health left!");
        }
    }

    //deals dmg to enemies
    public void attack(double[] avg){
        double rng = rand.nextDouble();
        double swingMulti = avg[0] + avg[1] + avg[2];
        double totalDmg;
        if (rng > 0.8) {
            System.out.println("Critical hit! That must have hurt!");
            totalDmg = dmg*swingMulti*weapon.getDmgMultiplier()*2.0;
        } else if (rng < 0.15) {
            System.out.println("Weak hit! The " + UIClient.fightingEnemy + " partially dodged your hit.");
            totalDmg = dmg*swingMulti*weapon.getDmgMultiplier()*0.5;
        } else {
            totalDmg = dmg*swingMulti*weapon.getDmgMultiplier();
        }
        if (avg[0] + avg[1] + avg[2] < 2) {
            System.out.println("Pro tip: You are supposed to be KILLING the enemy, not tickling them! 0 damage done.");
        } else UIClient.fightingEnemy.takeDmg(totalDmg);
    }

    // death function
    public void death() throws InterruptedException {
        nextLivingState();
        printLivingStatus();
        Thread.sleep(2000);
        System.out.println("Welcome to being dead. Sucks to be you! Normally this is the end of the path for any other person; however, you aren't any other person so I'm gonna give you another chance :)."
                            + "\nBUTTTTTT, It wouldn't be fun to just let you go back, right? For this reason I need you to beg at a certain volume. Good luck!");
        Thread.sleep(5000);
        dBThreshold = rand.nextDouble(1, 16);
        boolean gotThreshold = false;
        double avg;
        while (!gotThreshold) {
            avg = TCP_Client.getInstance().getAvgSound(2);
            if(avg < (dBThreshold - 0.6)) System.out.println("Quieter!");
            else if (avg > (dBThreshold + 0.6)) System.out.println("Louder!");
            else {
                gotThreshold = true;
                System.out.println("You got it!");
            }
        }
        respawn();
    }

    // respawn function
    public void respawn() {
        System.out.println("Alright I had enough of your begging. I don't want to hear you again, so get back in there!");
        nextLivingState();
        printLivingStatus();
        currentLocation = House.getInstance();
        health = 100;
        updateNearby();
    }

    // set weapon strategy
    public void setWeapon(Weapon w){
        weapon = w;
    }

    // getter for weapon (cashier needs it to check the current weapon before upgrading)
    public Weapon getWeapon(){
        return weapon;
    }

    //equip item
    public void equip(Item item){
        equipped = item;
    }

    //unequips the equipped item
    public void unequip(){
        equipped = null;
    }

    //getter for equipped
    public Item getEquipped(){
        return equipped;
    }

    

    //updates nearby to the arraylist of the current location
    public void updateNearby(){
        currentLocation.getNearby(nearby);
    }

    //print inventory
    public void showInv(){
        System.out.print("Inventory: [ ");
        for(int x = 0; x < inventory.size(); x++){
            if(x == inventory.size()-1) System.out.print(inventory.get(x) + " ]\n");
            else System.out.print(inventory.get(x) + ", ");
        }
        if(equipped != null) System.out.println("Currently Equipped: " + equipped);
        else System.out.println("Currently Equipped: none");
        System.out.println("Weapon: " + sword);
        System.out.println("Coins: " + coins);
    }

    //adds item to inv
    public void addItem(Item item){
        if(!inventory.contains(item)) inventory.add(item);
        else System.out.println(item + " is already in the inventory");
    }

    //removes item from inv
    public void removeItem(Item item){
        if(inventory.contains(item)) inventory.remove(item);
        else System.out.println(item + " is not in the inventory");
    }

    //check if inv empty
    public boolean isInvEmpty(){
        return inventory.isEmpty();
    }
    
    // inv getter
    public ArrayList<Item> getInv(){
        return inventory;
    }

    // getter for ingredients array
    public ArrayList<Ingredient> getIngredients(){
        return ingredients;
    }

    // go to next state
    public void nextState() {
        locationLockState.next(this);
    }

    // print current state
    public void printStatus() {
        locationLockState.printStatus();
    }

    // set the state (used by state children)
    public void setState(State s) {
        locationLockState = s;
    }

    public State getState() {
        return locationLockState;
    }

    // print current living state
    public void printLivingStatus() {
        livingState.printStatus();
    }

    // go to next living state
    public void nextLivingState() {
        livingState.next(this);
    }

    // set the living state (used by alive/dead states (children of State))
    public void setLivingState(State s) {
        livingState = s;
    }

    public State getLivingState(){
        return livingState;
    }

    // function to check nearby locations
    public void checkNearby() {
        for(int i=0; i<nearby.size();i++){
            System.out.println(nearby.get(i));
        }
    }

    //function that prints descriptions when user inputs look around
    public void look(){
        System.out.println(currentLocation.getDescription());
        if (currentLocation.enemy != null) System.out.println(currentLocation.enemy.description);
        for(int i=0; i<currentLocation.items.size() && !currentLocation.items.isEmpty();i++){
            if(!currentLocation.items.get(i).getDescription().equals("")) System.out.println(currentLocation.items.get(i).getDescription());
        }
        for(int i=0; i<currentLocation.npcs.size() && !currentLocation.npcs.isEmpty();i++){
            System.out.println(currentLocation.npcs.get(i).getDescription());
        }
    }

    public void addCoins(int numcoins) {
        coins += numcoins;
    }

    public void deductCoins(int numcoins) {
        coins -= numcoins;
    }

    public int getCoins() {
        return coins;
    }

    public void resetHealth(){
        health = 100;
    }
}
