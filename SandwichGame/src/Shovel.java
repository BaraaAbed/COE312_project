public class Shovel extends Item {

    Shovel() {
        takable = true;
        description = "There is a shovel lying down at the entrance of the castle. Perhaps it could be helpful to dig up something...";
    }

    public void use() {
        System.out.println("Digging with shovel");
    }

    @Override
    public String toString() {
        return "Shovel";
    }
}
