import java.io.IOException;

public class Main {
    public static void main(String[] args){
        System.out.println("TESTING 123");
        System.out.println("haha i am here");
        System.out.println("testing lol");
        int x=0;
        try{
            x = System.in.read();
        }
        catch(IOException e){

        }
        System.out.println("Your value is: "+(char)x);
    }
}
