import java.util.ArrayList;

public class Player {
    //initialzing variables
    public static Location currentLocation;
    private static double health;
    private ArrayList<Location> nearby;
    private ArrayList<Item> inventory;
    private Item equipped;
    private double dmg;//base dmg
    private static Player instance;//singleton

    //Constructor
    private Player(){
        currentLocation = new House();
        health = 100.0;
        dmg = 10.0;
        updateNearby();
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
        health -= _dmg;
    }

    //deals dmg to enemies
    public void attack(){
        double totalDmg = dmg*equipped.getDmgMultiplier();
        UI.fightingEnemy.takeDmg(totalDmg);
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
        nearby = currentLocation.getNearby();
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

}
