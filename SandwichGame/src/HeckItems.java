public abstract class HeckItems extends Item {
    //constructor
    public HeckItems(){
        takable = false;
    }

    public void use() {
        System.out.println("Heck's kitchen item sabotaged!");
    }

    public void sabotage(){
        System.out.println("You start messing around until you find a screw which, if removed, could break the station." + 
        "You have to finish this quick if you don't want to get caught.");
        fristDamage();
        if(!UIClient.failedSabo) secondDamage();
        if(!UIClient.failedSabo) thirdDamage();
        if(!UIClient.failedSabo) System.out.println("Successfully Sabotaged");
    }

    public abstract void fristDamage();
    public abstract void secondDamage();
    public abstract void thirdDamage();
}
