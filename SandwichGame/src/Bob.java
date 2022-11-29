import java.util.ArrayList;
import java.util.Scanner;

public class Bob extends NPC {
    private static Bob instance;
    private ArrayList<Command> commands; // Bob is invoker class of command design pattern

    private Bob() {
        description = "You find Bob doing the usual at the counter... you can ask him about the ingredients you need";
        commands = new ArrayList<Command>();
        commands.add(new IngredientLocationCommand());
        commands.add(new UpgradeWeaponCommand());
    }

    //gets instance (for singleton)
    public static synchronized Bob getInstance(){
        if(instance == null) instance = new Bob();
        return instance;
    }

    @Override
    public String toString() {
        return "Bob";
    }

    public void chooseOption(int option) { // chosen option from available commands
        commands.get(option - 1).execute();
    }

    public void menu() {
        int option;
        Scanner scan = new Scanner(System.in);

        do {
            System.out.println("Bob's Menu");
            System.out.println("1. Show legendary ingredients locations");
            System.out.println("2. Upgrade sword");
            System.out.println("3. Close menu");
            System.out.println("Choose option: ");
            option = scan.nextInt();
            scan.nextLine(); // to remove extra new line

            if(option == 1 || option == 2){
                chooseOption(option);
            }
            else if(option == 3){
                System.out.println("Closing menu...");
            }
            else {
                System.out.println("Invalid option entered. Please enter another option.");
            }

        } while (option != 3);
    }
}
