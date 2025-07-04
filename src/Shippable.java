public class Shippable implements ShippableBehavior {
    private String name;
    private double weight;

    Shippable(String name, double weight) {
        this.name = name;
        this.weight = weight;
    }

    public String getName(){
        return name;
    }

    public double getWeight(){
        return weight;
    }
}
