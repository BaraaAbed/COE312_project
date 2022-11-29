public abstract class Weapon extends Item{

    protected double dmgMultiplier;

    public Weapon(){
        dmgMultiplier = 1;
    }

    //getter for dmgMultiplier
    public double getDmgMultiplier(){
        return dmgMultiplier;
    }

    public void use(){
        //do nothing
    }
}
