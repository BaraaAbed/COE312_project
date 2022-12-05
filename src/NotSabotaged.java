public class NotSabotaged implements State {

    @Override
    public void next(Player player) {
        //do nothing        
    }

    @Override
    public void printStatus() {
        System.out.println("You have not attempted to sabotage this station yet.");
    }

    @Override
    public void next(HeckItems heckItems) {
        heckItems.setState(new Sabotaged());        
    }
    
}
