package LLD_OfferManagementSystem;

import java.util.HashMap;
import java.util.Map;

public class OfferManagementSystem {
    public static void main(String[] args) {
        Database db = new Database();

        // Register a user
        User user = new User("1", "John Doe", "john.doe@example.com");
        db.getAuthService().registerUser(user);

        // Add products
        Product product1 = new Product("p1", "Product 1", 100.0);
        Product product2 = new Product("p2", "Product 2", 150.0);
        db.getProductService().addProduct(product1);
        db.getProductService().addProduct(product2);

        // Create an offer
        Map<String, String> offerDetails = new HashMap<>();
        offerDetails.put("Buy", "1");
        offerDetails.put("Get", "1");
        Offer offer = new Offer("o1", "Buy X Get Y", offerDetails);
        db.getOfferService().createOffer(offer);

        // Add products to cart
        db.getCartService().addToCart(user.getId(), product1);
        db.getCartService().addToCart(user.getId(), product2);

        // Place order
        Cart cart = db.getCartService().getCart(user.getId());
        Order order = new Order("o1", user.getId(), cart.getProducts(), cart.getTotalPrice());
        db.getOrderService().placeOrder(order);

        System.out.println("Order placed successfully for user: " + user.getName());
    }
}

