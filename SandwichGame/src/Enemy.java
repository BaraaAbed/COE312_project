import java.text.DecimalFormat;

public abstract class Enemy {
    //initializing variables
    protected double health;
    protected double dmg;
    protected String description;
    protected double baseHealth;
    protected DecimalFormat dec;

    public Enemy() {
        this.dec = new DecimalFormat();
        dec.setMaximumFractionDigits(1);
    }

    ///Fighting mechanics
    //Start fight
    public void fight(){
        UIClient.fighting = true;
        UIClient.fightingEnemy = this;
    }
    
    //attack player
    public void attack(){//attack
        System.out.print("The " + this + " did " + dmg + " damage to you. ");
        Player.getInstance().takeDmg(dmg);
    }

    //function for taking dmg
    public void takeDmg(double _dmg){
        health -= _dmg;
        if (health <= 0){
            health = 0;
            System.out.println("You have successfully killed the " + this);
            Player.getInstance().resetHealth();
            Player.currentLocation.enemy = null;
            makeTakable();
        } else {
            System.out.println("You did " + dec.format(_dmg) + " damage to the enemy! The " + this + " has " + dec.format(health) + " health left!");
        }
    }

    //health getter
    public double getHealth(){
        return health;
    }

    //make takable
    public void makeTakable(){
        //will be overwritten
    }

    public abstract double getAttackDur();
    public abstract double getDodgeDur();
}
