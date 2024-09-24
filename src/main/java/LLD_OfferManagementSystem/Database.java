package LLD_OfferManagementSystem;

public class Database {
    private AuthenticationService authService;
    private OfferManagementService offerService;
    private ProductCatalogService productService;
    private CartService cartService;
    private OrderService orderService;

    public Database() {
        this.authService = new AuthenticationService();
        this.offerService = new OfferManagementService();
        this.productService = new ProductCatalogService();
        this.cartService = new CartService();
        this.orderService = new OrderService(new NotificationService(), new AnalyticsService());
    }

    public AuthenticationService getAuthService() {
        return authService;
    }

    public OfferManagementService getOfferService() {
        return offerService;
    }

    public ProductCatalogService getProductService() {
        return productService;
    }

    public CartService getCartService() {
        return cartService;
    }

    public OrderService getOrderService() {
        return orderService;
    }
}
