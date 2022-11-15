
public class Enemy {
    //initializing variables
    private double health;
    private double dmg;

    ///Fighting mechanics
    //Start fight
    public void fight(){
        UI.fighting = true;
        UI.fightingEnemy = this;
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
