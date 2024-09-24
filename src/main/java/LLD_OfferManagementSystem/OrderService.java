package LLD_OfferManagementSystem;

import java.util.HashMap;
import java.util.Map;

public class OrderService {
    private Map<String, Order> orders;
    private NotificationService notificationService;
    private AnalyticsService analyticsService;

    public OrderService(NotificationService notificationService, AnalyticsService analyticsService) {
        this.orders = new HashMap<>();
        this.notificationService = notificationService;
        this.analyticsService = analyticsService;
    }

    public void placeOrder(Order order) {
        orders.put(order.getId(), order);
        notificationService.sendNotification(new User(order.getUserId(), "", ""), "Order placed: " + order.getId());
        analyticsService.logOfferUsage(new Offer("", "order", new HashMap<>()));
    }
}
