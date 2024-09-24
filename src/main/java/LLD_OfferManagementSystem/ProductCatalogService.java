package LLD_OfferManagementSystem;

import java.util.HashMap;
import java.util.Map;

public class ProductCatalogService {
    private Map<String, Product> products;

    public ProductCatalogService() {
        this.products = new HashMap<>();
    }

    public void addProduct(Product product) {
        products.put(product.getId(), product);
    }

    public Product getProduct(String productId) {
        return products.get(productId);
    }
}
