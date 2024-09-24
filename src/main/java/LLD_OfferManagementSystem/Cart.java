package LLD_OfferManagementSystem;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private String userId;
    private List<Product> products;
    private double totalPrice;

    // Constructors, getters, and setters
    public Cart(String userId) {
        this.userId = userId;
        this.products = new ArrayList<>();
        this.totalPrice = 0.0;
    }

    public void addProduct(Product product) {
        this.products.add(product);
        this.totalPrice += product.getPrice();
    }

    public List<Product> getProducts() {
        return products;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
}
