import java.util.ArrayList;
import java.util.List;

/* Handles the checkout process: validates cart items, calculates total cost,
   deducts balance, reduces stock, and triggers shipping if needed
*/ 
public class CheckoutService {
    private ShippingService shippingService;
    private static final double SHIPPING_RATE_PER_KG = 27.0;

    public CheckoutService(ShippingService shippingService) {
        this.shippingService = shippingService;
    }

    public void checkout(Customer customer) {
        Cart cart = customer.getCart();
        if (cart.isEmpty()) {
            throw new IllegalStateException("Cart is empty.");
        }

        List<CartItem> items = cart.getItems();
        double subtotal = 0.0;
        List<CartItem> shippableItems = new ArrayList<>();

        for (CartItem item : items) {
            Product product = item.getProduct();
            int quantity = item.getQuantity();

            // checks stock
            if (!product.isInStock(quantity)) {
                throw new IllegalStateException(product.getName() + " is out of stock.");
            }

            // checks expiration
            product.getExpirable().ifPresent(expirable -> {
                if (expirable.isExpired()) {
                    throw new IllegalStateException(product.getName() + " is expired.");
                }
            });

            // collects shippable items together
            if (product.getShippable().isPresent()) {
                shippableItems.add(item);
            }

            subtotal += item.getSubtotal();
        }

        // calculates shipping fee
        double shippingFee = calculateShippingFee(shippableItems);
        double total = subtotal + shippingFee;

        if (customer.getBalance() < total) {
            throw new IllegalStateException("Insufficient balance.");
        }

        // updates stock and deducts balance
        for (CartItem item : items) {
            item.getProduct().decreaseAvailable(item.getQuantity());
        }

        customer.deductBalance(total);

        // triggers shipping
        if (!shippableItems.isEmpty()) {
            shippingService.ship(shippableItems);
        }

        double remaining = customer.getBalance();

        printSummary(items, subtotal, shippingFee, total, remaining);
    }

    // calculates total shipping fees based on weight and rate per kilogram
    private double calculateShippingFee(List<CartItem> items) {
        return items.stream()
                .mapToDouble(item -> {
                    double weight = item.getProduct().getShippable().get().getWeight(); // safe because you already
                                                                                        // filtered
                    return weight * item.getQuantity();
                })
                .sum() * SHIPPING_RATE_PER_KG;
    }

    // prints summary of the transaction, including subtotal, shipping, and remainder balance
    private void printSummary(List<CartItem> items, double subtotal, double shipping, double total, double remaining) {
        System.out.println("** Checkout Receipt **");
        for (CartItem item : items) {
            System.out.printf("%dx %s %.0f\n", item.getQuantity(), item.getProduct().getName(), item.getSubtotal());
        }
        System.out.println("----------------------");
        System.out.printf("Subtotal %.0f\n", subtotal);
        System.out.printf("Shipping %.0f\n", shipping);
        System.out.printf("Amount %.0f\n", total);
        System.out.printf("Remaining %.0f\n", remaining);

    }
}
