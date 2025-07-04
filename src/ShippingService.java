import java.util.List;

// handles printing shipment notice for all shippable items
public class ShippingService {
    public void ship(List<CartItem> items) {
        if (items.isEmpty()) return;

        System.out.println("** Shipment Notice **");

        double totalWeight = 0;

        for (CartItem item : items) {
            Product product = item.getProduct();
            int quantity = item.getQuantity();

            ShippableBehavior shippable = product.getShippable().get(); // safe because we filtered earlier
            double itemTotalWeight = shippable.getWeight() * quantity;

            // prints quantity, name, and total weight in grams
            System.out.printf("%dx %s %.0fg\n", quantity, product.getName(), itemTotalWeight * 1000);
            totalWeight += itemTotalWeight;
        }

        // print total package weight in kilograms
        System.out.printf("Total package weight %.1fkg\n", totalWeight);
        System.out.println();
    }
}
