import java.util.Scanner;

public class UI extends ConcreteSubject implements Runnable {
    //Variables
    public Thread t;
    public static Scanner scan;
    private Message msg;
    private static UI instance;

    //Constructor
    private UI(){
        t = new Thread(this);
        scan = new Scanner(System.in);
        msg = new Message(this, "Command", null);
        t.start();
    }

    //gets instance (for singleton)
    public static synchronized UI getInstance(){
        if(instance == null) instance = new UI();
        return instance;
    }

    @Override
    public void run() {
        while(scan.hasNextLine()){
            msg.payload = scan.nextLine();
            publishMessage(msg);
        }
    }

    
}
