public class AliveState implements State {
    public void next(Player player) {
        player.setLivingState(new DeadState());
    }

    public void printStatus() {
        System.out.println("You are now alive and kickin'! You have respawned in your house.");
    }

    public void next(HeckItems heckItems) {
        // do nothing
    }
}
