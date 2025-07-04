import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Represents a shopping cart containing products and their quantities
public class Cart {
    List<CartItem> cartItems;

    public Cart() {
        cartItems = new ArrayList<>();
    }

    public void add(Product product, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive.");
        }
        if (!product.isInStock(quantity)) {
            throw new IllegalArgumentException("Not enough stock for product: " + product.getName());
        }

        Optional<CartItem> existingItem = findItemByProduct(product);
        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            int newQty = item.getQuantity() + quantity;
            if (!product.isInStock(newQty)) {
                throw new IllegalArgumentException("Not enough stock to increase quantity for: " + product.getName());
            }
            item.setQuantity(newQty);
        } else {
            cartItems.add(new CartItem(product, quantity));
        }
    }

    public double getSubtotal() {
        return cartItems.stream()
                .mapToDouble(CartItem::getSubtotal)
                .sum();
    }

    public List<CartItem> getItems() {
        return cartItems;
    }

    public boolean isEmpty() {
        return cartItems.isEmpty();
    }

    // Searches for an existing item in the cart based on the product
    private Optional<CartItem> findItemByProduct(Product product) {
        return cartItems.stream()
                .filter(item -> item.getProduct().equals(product))
                .findFirst();
    }

}
