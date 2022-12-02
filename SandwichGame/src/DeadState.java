public class DeadState implements State {
    public void next(Player player) {
        player.setLivingState(new AliveState());
    }

    public void printStatus() {
        System.out.println("You died!");
    }

    public void next(HeckItems heckItems) {
        // do nothing
    }
}
