import java.util.ArrayList;

public class LegendarySandwich extends Sandwich {
    // this class is a type of sandwich (child)
    // which implements the addIngredients() abstract function
    // which is part of the Sandwich template class

    public boolean addIngredients() {

        // making a checklist of all ingredients to check against Player ingredients array
        ArrayList<Ingredient> checklist = new ArrayList<Ingredient>();
        checklist.add(Bread.getInstance());
        checklist.add(Sauce.getInstance());
        checklist.add(Tomato.getInstance());
        checklist.add(Lettuce.getInstance());
        checklist.add(Cheese.getInstance());
        checklist.add(Mushroom.getInstance());
        checklist.add(Meat.getInstance());

        if(Player.getIngredients().containsAll(checklist)) {
            // link to TCP client to detect phone tilting to call template functions
            System.out.println("Adding legendary meat...");
            System.out.println("Adding legendary cheese...");
            System.out.println("Adding legendary tomato...");
            System.out.println("Adding legendary lettuce...");
            System.out.println("Adding legendary mushroom...");
            System.out.println("Adding legendary sauce...");
            return true;
        }
        else {
            System.out.println("Cannot make sandwich as you do not have all the ingredients yet.");
            return false;
        }
        
    }

}
