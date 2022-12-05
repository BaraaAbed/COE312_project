import java.util.ArrayList;

public class Driver {
    public static void main(String[] args) throws Exception {
        ArrayList<ConcreteSubject> subjects = new ArrayList<ConcreteSubject>();
        TCP_Client tcp = TCP_Client.getInstance();
        UI ui = UI.getInstance();
        subjects.add(ui);
        subjects.add(tcp);
        UIClient uiClient = UIClient.getInstance(subjects);
        uiClient.t.join();
    }
}