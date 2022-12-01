import java.util.ArrayList;
import java.util.InputMismatchException;

public class Bob extends NPC {
    private static Bob instance;
    private ArrayList<Command> commands; // Bob is invoker class of command design pattern
    private String[] commInput;

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

    public void talk() {
        int option = 999;

        do {
            try{
                System.out.println("Bob's Menu");
                System.out.println("1. Show legendary ingredients locations");
                System.out.println("2. Upgrade sword");
                System.out.println("3. Close menu");
                System.out.println("Choose option: ");

                commInput = UIClient.getCommInput();

                // checking if ONE word/value entered AND it is an INTEGER
                if(commInput.length > 4) { // more than one word + 3 padding words (by default at end of commInput)
                    System.out.println("Please enter one number to choose an option");
                    continue;
                }
                else {
                    try {
                        option = Integer.parseInt(commInput[0]);
                    }
                    catch(NumberFormatException e) {
                        System.out.println("You did not enter a number! Please enter one number to choose an option");
                        continue;
                    }
                }

                // at this point the user has entered ONE word/value AND it is an INTEGER
                // can now check the option entered

                if(option == 1 || option == 2){
                    chooseOption(option);
                }
                else if(option == 3){
                    System.out.println("Closing menu...");
                }
                else {
                    System.out.println("Invalid option entered. Please enter another option.");
                }
            } catch (InputMismatchException in) {
                System.out.println("Seems like you have nothing to talk about. How about you get lost then."); option = 3;
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        } while (option != 3);
    }
}
