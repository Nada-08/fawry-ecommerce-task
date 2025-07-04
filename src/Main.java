import java.time.LocalDate;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        // Define behaviors
        ExpirableBehavior cheeseExpiry = new Expirable(LocalDate.now().plusDays(2));

        ShippableBehavior cheeseShippable = new Shippable("Cheese", 0.2);

        ShippableBehavior biscuitsShippable = new Shippable("Biscuits", 0.7);

        // Create products
        Product cheese = new Product("Cheese", 100, 100,
                Optional.of(cheeseExpiry),
                Optional.of(cheeseShippable));

        Product biscuits = new Product("Biscuits", 150, 150,
                Optional.empty(),
                Optional.of(biscuitsShippable));

        Product scratchCard = new Product("Scratch Card", 50, 20,
                Optional.empty(),
                Optional.empty());

        // Setup customer and cart
        Customer customer = new Customer("Nada", 1000);
        Cart cart = customer.getCart();

        cart.add(cheese, 2);
        cart.add(biscuits, 1);
        cart.add(scratchCard, 1);

        // Perform checkout
        ShippingService shippingService = new ShippingService();
        CheckoutService checkoutService = new CheckoutService(shippingService);

        try {
            checkoutService.checkout(customer);
        } catch (Exception e) {
            System.out.println("Checkout failed: " + e.getMessage());
        }
    }
}
