
public class Enemy {
    //initializing variables
    protected double health;
    protected double dmg;
    protected String description;

    ///Fighting mechanics
    //Start fight
    public void fight(){
        UIClient.fighting = true;
        UIClient.fightingEnemy = this;
    }
    
    //attack player
    public void attack(){//attack
        Player.takeDmg(dmg);
    }

    //function for taking dmg
    public void takeDmg(double _dmg){
        health -= _dmg;
    }

    //health getter
    public double getHealth(){
        return health;
    }
}
