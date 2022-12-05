import java.util.ArrayList;

public class ConcreteObserver implements Observer {
    private ArrayList<ConcreteSubject> subjects;

    public ConcreteObserver(ArrayList<ConcreteSubject> _subjects) {
        //subjects = (ArrayList<ConcreteSubject>) _subjects.clone();
        subjects = _subjects;

        for (int i =0; i < subjects.size(); i++) {
            subjects.get(i).registerObserver(this);
        }
    }
    
    public void update(Message m) {
        // implemented in child
    }
}
