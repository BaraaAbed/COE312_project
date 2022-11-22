import java.util.ArrayList;

public class Driver {
    public static void main(String[] args) throws Exception {
        Commands com = new Commands();
        ArrayList<ConcreteSubject> subjects = new ArrayList<ConcreteSubject>();
        TCP_Client tcp = new TCP_Client("10.25.147.85", 58384);
        subjects.add(com);
        UIClient uiClient = new UIClient(subjects);
        uiClient.t.join();
    }
}
