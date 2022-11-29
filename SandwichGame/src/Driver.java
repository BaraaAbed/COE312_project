import java.util.ArrayList;

public class Driver {
    public static void main(String[] args) throws Exception {
        ArrayList<ConcreteSubject> subjects = new ArrayList<ConcreteSubject>();
        TCP_Client tcp = new TCP_Client();
        UI ui = new UI();
        subjects.add(ui);
        subjects.add(tcp);
        UIClient uiClient = new UIClient(subjects);
        uiClient.t.join();
    }
}