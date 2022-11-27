import java.util.Scanner;

public class UI extends ConcreteSubject implements Runnable {
    //Variables
    public Thread t;
    private Scanner scan;
    private Message msg;

    //Constructor
    public UI(){
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