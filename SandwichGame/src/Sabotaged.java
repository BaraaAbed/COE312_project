public class Sabotaged implements State {

    @Override
    public void next(Player player) {
        //do nohting
    }

    @Override
    public void printStatus() {
        System.out.println("You have already attempted this station. The same trick won't work twice.");        
    }

    @Override
    public void next(HeckItems heckItems) {
        heckItems.setState(new NotSabotaged());        
    }
    
}
