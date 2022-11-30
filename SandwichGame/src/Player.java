import java.util.ArrayList;
import java.util.Random;

public class Player {
    //initialzing variables
    public static Location currentLocation;
    private static double health;
    public ArrayList<Location> nearby;
    private ArrayList<Item> inventory;
    private Item equipped;
    public String sword;
    public Weapon weapon; // strategy design pattern
    private static ArrayList<Ingredient> ingredients;
    private double dmg;//base dmg
    private static Player instance;//singleton
    private State locationLockState; // state design pattern
    private int coins; // keep track of num of coins
    private Random rand;

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
        dmg = 10.0;
        updateNearby();
        locationLockState = new MapLockedState();
    }

    //gets instance (for singleton)
    public static synchronized Player getInstance(){
        if(instance == null) instance = new Player();
        return instance;
    }

    //health getter
    public double getHealth(){
        return health;
    }

    //take dmg
    public void takeDmg(double _dmg){
        health -= _dmg;
        if (health <= 0) {
            System.out.println("You died! (x_x)");
            death();
        }
        else {
            System.out.println("You now have " + health + " health left!");
        }
    }

    //deals dmg to enemies
    public void attack(double[] avg){
        double rand1 = rand.nextDouble();
        double rand2 = rand.nextDouble();
        double rand3 = rand.nextDouble();
        double swingMulti = avg[0]*rand1 + avg[1]*rand2 + avg[2]*rand3;
        if ((rand1 + rand2 + rand3) > 2.6) System.out.println("Critical hit! That must have hurt!");
        else if ((rand1 + rand2 + rand3) < 0.8) System.out.println("Weak hit! The " + UIClient.fightingEnemy + " partially dodged your hit.");
        else if ((rand1 + rand2 + rand3) < 0.1) System.out.println("You're just unlucky mate. It's like you didn't even attack!");
        double totalDmg = dmg*swingMulti*weapon.getDmgMultiplier(); // using weapon strategy and rng/swing multiplier
        UIClient.fightingEnemy.takeDmg(totalDmg);
    }

    //death stuff
    public void death(){
        Player.health = 100.0;// change initial health if you change this
        currentLocation = House.getInstance();
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
    public static ArrayList<Ingredient> getIngredients(){
        return ingredients;
    }

    // go to next state (used in main/driver/ui)
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

    public State getState(){
        return locationLockState;
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
}
