import java.util.ArrayList;

public class ConcreteObserver implements Observer {
    private ArrayList<ConcreteSubject> subjects;

    public ConcreteObserver(ArrayList<ConcreteSubject> subjects) {
        subjects = new ArrayList<ConcreteSubject>();

        for (int i =0; i < subjects.size(); i++) {
            subjects.get(i).registerObserver(this);
        }
    }
    
    public void update(Message m) {
        // implemented in child
    }
}
