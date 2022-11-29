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
            "Fridge: bread\n"+
            "Farm: lettuce\n"+
            "Warehouse: tomato\n"+
            "Forest: mushroom\n"+
            "Rat King's castle: cheese\n"+
            "Cave: meat\n"+
            "Heck's Kitchen: sauce\n");
        }
    }
}
