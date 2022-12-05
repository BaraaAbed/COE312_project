public interface State {
    public void next(Player player);
    public void printStatus();
    public void next(HeckItems heckItems);
}
