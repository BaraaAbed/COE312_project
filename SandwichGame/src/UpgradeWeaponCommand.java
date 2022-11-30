public class UpgradeWeaponCommand implements Command {
    Player p;
    int upgradePrice;

    UpgradeWeaponCommand(){
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
        }
        else {
            System.out.println("Bob: Sorry, you do not have "+upgradePrice+" coins to upgrade your sword");
            System.out.println("Bob: Go fight some baddies to collect more coins!");
        }
    }
}
