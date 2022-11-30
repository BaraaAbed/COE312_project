public class Shovel extends Item {

    Shovel() {
        description = "There is a shovel in between these crops...perhaps it could be helpful to dig up something...";
    }

    public void use() {
        System.out.println("Digging with shovel");
    }

    @Override
    public String toString() {
        return "Shovel";
    }
}
