import java.util.ArrayList;

public class UIClient extends ConcreteObserver implements Runnable{
    //initializing variables
    public static boolean fighting;
    public static Enemy fightingEnemy;
    public Thread t;
    private String[] commInput;
    private boolean isUpdate;
    public static boolean getAcc;
    public static boolean getDB;
    public static boolean getHeading;
    public static boolean getOrientation;
    

    //constructor
    public UIClient(ArrayList<ConcreteSubject> subjects){
        super(subjects);
        getAcc = false;
        getDB = false;
        getHeading = false;
        getOrientation = false;
        isUpdate = false;
        t = new Thread(this);
        t.start();
    }

    @Override
    public void update(Message m) {
        switch (m.topic){
            case "Command":
            commInput = m.payload.toString().split(" ");
            isUpdate = true;
            break;
        }
    }

    @Override
    public void run() {
        while(true){
            try { 
                Thread.sleep(1); //needed for stupid isUpdate to work
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(isUpdate == true){
                System.out.println(commInput[0]);
                isUpdate = false;
            }
        }
        
    }


}
