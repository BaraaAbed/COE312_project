public class UpgradeWeaponCommand implements Command {
    Player p;
    int upgradePrice;
    Audio audio;

    UpgradeWeaponCommand(){
        audio = Audio.getInstance();
        p = Player.getInstance();
        upgradePrice = 30;
    }

    public void execute() {
        if(p.getCoins() >= upgradePrice) {
            if(p.getWeapon() instanceof LowDamageWeapon){
                p.setWeapon(new MediumDamageWeapon());
                p.deductCoins(upgradePrice);
                p.sword = "Iron sword";
                System.out.println("Bob: Congratulations! You have upgraded your weapon from a stone sword to an iron sword "+
                "with a damage multiplier of "+new MediumDamageWeapon().getDmgMultiplier()+"!");
                System.out.println("Remaining coins: "+p.getCoins());
            }
            else if(p.getWeapon() instanceof MediumDamageWeapon){
                p.setWeapon(new HighDamageWeapon());
                p.deductCoins(upgradePrice);
                p.sword = "Diamond sword";
                System.out.println("Bob: Congratulations! You have upgraded your weapon from an iron sword to a diamond sword "+
                "with a damage multiplier of "+new HighDamageWeapon().getDmgMultiplier()+"!");
                System.out.println("You now possess the highest damage weapon in the game! Use it wisely...");
                System.out.println("Remaining coins: "+p.getCoins());
            }
            audio.playSound("upgrade");
        }
        else {
            System.out.println("Bob: To upgarde your weapon, you need 30 coins. This is a supermarket, not a charity!\n"
            + "(Hint: picking up an ingredient gives you 10 coins)");
        }
    }
}
