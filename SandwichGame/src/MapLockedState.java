public class MapLockedState implements State {
    public void next(Player player) {
        player.setState(new MapUnlockedState());
    }
    
    public void printStatus() {
        System.out.println("Map locations are locked. Player must go to supermarket first to unlock the locations");
    }
}
