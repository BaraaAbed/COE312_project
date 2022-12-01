public class IngredientLocationCommand implements Command {
    Player p;

    IngredientLocationCommand() {
        p = Player.getInstance();
    }
    
    public void execute() {
        if(p.getState() instanceof MapLockedState){
            System.out.println("Bob: You cannot view the legendary ingredients' locations, since you have not unlocked the map yet.");
        }
        else if(p.getState() instanceof MapUnlockedState) {
            System.out.println("Bob: Legendary ingredients' locations:\n"+
            "Fridge: Golden Bread\n"+
            "Haunted Farm: Legendary Lettuce\n"+
            "Abandoned Warehouse: Legendary Tomato\n"+
            "Forest: Magic Mushroom\n"+
            "Rat King's castle: Legendary Cheese\n"+
            "Volcano Cave: Phoenix Meat\n"+
            "Heck's Kitchen: Lamb Sauce");
        }
    }
}
