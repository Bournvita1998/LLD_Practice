package LLD_NotificationService;

public class PushNotificationChannel implements NotificationChannel {
    @Override
    public void send(Notification notification) throws Exception {
        // Implementation for sending push notification
        System.out.println("Sending Push Notification: " + notification.getMessage());
    }
}
