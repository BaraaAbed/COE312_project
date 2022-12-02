public class DeadState implements State {
    public void next(Player player) {
        player.setLivingState(new AliveState());
    }

    public void printStatus() {
        System.out.println("\nYou are now dead.");
    }

    public void next(HeckItems heckItems) {
        // do nothing
    }
}
