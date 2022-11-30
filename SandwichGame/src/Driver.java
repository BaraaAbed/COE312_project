import java.util.ArrayList;

public class Driver {
    public static void main(String[] args) throws Exception {
        ArrayList<ConcreteSubject> subjects = new ArrayList<ConcreteSubject>();
        TCP_Client tcp = new TCP_Client();
        UI ui = UI.getInstance();
        subjects.add(ui);
        subjects.add(tcp);
        UIClient uiClient = UIClient.getInstance(subjects);
        uiClient.t.join();
    }
}