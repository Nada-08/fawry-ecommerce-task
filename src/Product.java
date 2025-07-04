import java.util.Optional;

public class Product {
    private String name;
    private double price;
    private int available;

    private Optional<ExpirableBehavior> expirable;
    private Optional<ShippableBehavior> shippable;

    Product(String name, int available, double price, Optional<ExpirableBehavior> expirable,
            Optional<ShippableBehavior> shippable) {
        this.name = name;
        this.available = available;
        this.price = price;
        this.expirable = expirable;
        this.shippable = shippable;
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double newPrice) {
        this.price = newPrice;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public Optional<ExpirableBehavior> getExpirable() {
        return expirable;
    }

    public Optional<ShippableBehavior> getShippable() {
        return shippable;
    }

    public boolean isInStock(int quantity) {
        return quantity > 0 && quantity <= available;
    }

    public void decreaseAvailable(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount must be positive.");
        }
        if (amount > available) {
            throw new IllegalArgumentException("Not enough stock.");
        }
        this.available -= amount;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Product other = (Product) obj;
        return name != null && name.equals(other.name);
    }

    @Override
    public int hashCode() {
        return name == null ? 0 : name.hashCode();
    }

}
