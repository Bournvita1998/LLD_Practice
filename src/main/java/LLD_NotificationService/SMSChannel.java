package LLD_NotificationService;

public class SMSChannel implements NotificationChannel {
    @Override
    public void send(Notification notification) throws Exception {
        // Implementation for sending SMS
        System.out.println("Sending SMS: " + notification.getMessage());
    }
}
