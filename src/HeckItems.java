public abstract class HeckItems extends Item {
    //variables
    private State SaboState = new NotSabotaged();

    //constructor
    public HeckItems(){
        takable = false;
    }

    public void use() {
        System.out.println("Heck's kitchen item sabotaged!");
    }

    final public void sabotage() throws InterruptedException{
        System.out.println("You start messing around until you find a screw which, if removed, could break the station." + 
        " You have to finish this quick if you don't want to get caught.");
        Thread.sleep(3000);
        firstDamage();
        Thread.sleep(2000);
        if(!UIClient.failedSabo) secondDamage();
        Thread.sleep(2000);
        if(!UIClient.failedSabo) thirdDamage();
        if(!UIClient.failedSabo) System.out.println("Successfully Sabotaged");
    }

    public abstract void firstDamage();
    public abstract void secondDamage();
    public abstract void thirdDamage();

    // go to next state (used in main/driver/ui)
    public void nextState() {
        SaboState.next(this);
    }

    // print current state
    public void printStatus() {
        SaboState.printStatus();
    }

    // set the state (used by state children)
    public void setState(State s) {
        SaboState = s;
    }

    //state getter
    public State getState(){
        return SaboState;
    }
}
