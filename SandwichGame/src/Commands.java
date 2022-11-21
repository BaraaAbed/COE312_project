import java.util.Scanner;

public class Commands extends ConcreteSubject implements Runnable {
    //Variables
    public Thread t;
    private Scanner scan;
    private Message msg;

    //Constructor
    public Commands(){
        t = new Thread(this);
        scan = new Scanner(System.in);
        msg = new Message(this, "Command", null);
        t.start();
    }

    @Override
    public void run() {
        while(scan.hasNextLine()){
            msg.payload = scan.nextLine();
            publishMessage(msg);
        }
    }

    
}
