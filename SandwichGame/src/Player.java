import java.util.ArrayList;

public class Player {
    //initialzing variables
    public static Location currentLocation;
    private static double health;
    private ArrayList<Location> nearby;
    private ArrayList<Item> inventory;
    private Item equipped;
    private Weapon weapon; // strategy design pattern
    private static ArrayList<Ingredient> ingredients;
    private double dmg;//base dmg
    private static Player instance;//singleton
    private State locationLockState; // state design pattern

    //Constructor
    private Player(){
        currentLocation = House.getInstance();
        weapon = new LowDamageWeapon(); // starts with stone sword from cashier
        health = 100.0;
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
    public static void takeDmg(double _dmg){
        // add if statement to check if negative health, 
        // then died/respawn and reset health?
        health -= _dmg;
    }

    //deals dmg to enemies
    public void attack(){
        double totalDmg = dmg*weapon.getDmgMultiplier(); // using weapon strategy
        UIClient.fightingEnemy.takeDmg(totalDmg);
    }

    // set weapon strategy
    public void setWeapon(Weapon w){
        weapon = w;
    }

    //equip item
    public void equip(Item item){
        equipped = item;
    }

    //unequips the equipped item
    public void unequip(){
        equipped = null;
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
        System.out.println("Currently Equipped: " + equipped);
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

    // function to check nearby locations
    public void checkNearby() {
        for(int i=0; i<nearby.size();i++){
            System.out.println(nearby.get(i));
        }
    }
}
