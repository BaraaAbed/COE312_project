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

    public void sabotage(){
        System.out.println("You start messing around until you find a screw which, if removed, could break the station." + 
        "You have to finish this quick if you don't want to get caught.");
        fristDamage();
        if(!UIClient.failedSabo) secondDamage();
        if(!UIClient.failedSabo) thirdDamage();
        if(!UIClient.failedSabo) System.out.println("Successfully Sabotaged");
    }

    public abstract void fristDamage();
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
