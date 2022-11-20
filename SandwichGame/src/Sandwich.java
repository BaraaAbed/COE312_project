
public abstract class Sandwich {
    // this class is used for Template design pattern

    final public void makeSandwich() {

        if(Player.currentLocation instanceof Kitchen && Player.getIngredients().contains(Bread.getInstance())) {
            openBread();
            if(addIngredients()) {
                closeBread();
            }
            else {
                System.out.println("Returning bread to inventory.");
            }
        }
        else {
            System.out.println("Cannot make sandwich as you are not in the kitchen"
                                + "or you do not have bread.");
        }


    }

    protected void openBread(){
        System.out.println("Opening bread and placing it on the counter...");
    }

    abstract boolean addIngredients(); // child can implement order of adding ingredients

    protected void closeBread(){
        System.out.println("Closing bread...sandwich is complete!");
    }

}
