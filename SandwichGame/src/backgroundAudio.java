public class backgroundAudio implements Runnable{
    //variables
    Thread t;
    Player player;
    
    //constructor
    public backgroundAudio(){
        player = Player.getInstance();
        t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        
    }
}
