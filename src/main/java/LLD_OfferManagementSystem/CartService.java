package LLD_OfferManagementSystem;

import java.util.HashMap;
import java.util.Map;

public class CartService {
    private Map<String, Cart> carts;

    public CartService() {
        this.carts = new HashMap<>();
    }

    public Cart getCart(String userId) {
        return carts.computeIfAbsent(userId, Cart::new);
    }

    public void addToCart(String userId, Product product) {
        Cart cart = getCart(userId);
        cart.addProduct(product);
    }
}
