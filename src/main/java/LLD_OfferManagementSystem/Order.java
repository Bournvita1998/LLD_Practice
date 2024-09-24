package LLD_OfferManagementSystem;

import java.util.List;

public class Order {
    private String id;
    private String userId;
    private List<Product> products;
    private double totalPrice;

    // Constructors, getters, and setters
    public Order(String id, String userId, List<Product> products, double totalPrice) {
        this.id = id;
        this.userId = userId;
        this.products = products;
        this.totalPrice = totalPrice;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public List<Product> getProducts() {
        return products;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
}
