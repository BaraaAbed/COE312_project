import java.util.ArrayList;

public class Driver {
    public static void main(String[] args) throws Exception {
        Commands com = new Commands();
        ArrayList<ConcreteSubject> subjects = new ArrayList<ConcreteSubject>();
        TCP_Client tcp = new TCP_Client("192.168.0.123", 2000);
        subjects.add(com);
        subjects.add(tcp);
        UIClient uiClient = new UIClient(subjects);
        uiClient.t.join();
    }
}
